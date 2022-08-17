package com.example.inapppurchasedemo

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.billingclient.api.*
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.collect.ImmutableList
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), PurchasesUpdatedListener {
    private var billingClient: BillingClient? = null
    private var productDetails: ProductDetails? = null
    private var selectedOfferIndex: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpBillingClient()
        initListeners()
    }

    private fun initListeners() {
        if(productDetails == null) {
            return
        }
        // Retrieve a value for "productDetails" by calling queryProductDetailsAsync()
        // Get the offerToken of the selected offer
        val offerToken =
            selectedOfferIndex?.let { productDetails?.subscriptionOfferDetails?.get(it)?.offerToken }
        val productDetailsParamsList: List<BillingFlowParams.ProductDetailsParams> =
            listOf(
                BillingFlowParams.ProductDetailsParams.newBuilder()
                    .setProductDetails(productDetails!!)
                    .setOfferToken(offerToken!!)
                    .build()
            )
        val billingFlowParams =
            BillingFlowParams.newBuilder()
                .setProductDetailsParamsList(productDetailsParamsList)
                .build()

        // Launch the billing flow
        billingClient?.launchBillingFlow(this, billingFlowParams)
    }

    private fun setUpBillingClient() {
        billingClient = BillingClient.newBuilder(this)
            .setListener(purchaseUpdateListener)
            .enablePendingPurchases()
            .build()
        startConnection()
    }

    private fun startConnection() {
        billingClient?.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.
                    Log.v("TAG_INAPP", "Setup Billing Done")
                    queryAvailableProducts()
                }
            }

            override fun onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                startConnection()
            }
        })
    }

    private fun queryAvailableProducts() {
        val queryProductDetailsParams =
            QueryProductDetailsParams.newBuilder()
                .setProductList(
                    ImmutableList.of(
                        QueryProductDetailsParams.Product.newBuilder()
                            .setProductId("premium_10_demo")
                            .setProductType(BillingClient.ProductType.SUBS)
                            .build()))
                .build()

        billingClient?.queryProductDetailsAsync(queryProductDetailsParams) {
                billingResult, productDetailsList ->
            if(billingResult.responseCode == -1) {
                startConnection()
            }
            // Process the result
            for (productDetails in productDetailsList) {
                Log.v("TAG_INAPP", "skuDetailsList : $productDetailsList")
                //This list should contain the products added above
                updateUI(productDetails)
            }
        }

    }

    private fun updateUI(productDetails: ProductDetails?) {
        productDetails?.let {
            this.productDetails = it
            txt_product_name?.text = productDetails.title
            txt_product_description?.text = productDetails.description
            showUIElements()
        }
    }

    private fun showUIElements() {
        txt_product_name?.visibility = View.VISIBLE
        txt_product_description?.visibility = View.VISIBLE
        txt_product_buy?.visibility = View.VISIBLE
    }

    private val purchaseUpdateListener =
        PurchasesUpdatedListener { billingResult, purchases ->
            Log.v("TAG_INAPP", "billingResult responseCode : ${billingResult.responseCode}")

            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
                for (purchase in purchases) {
//                        handlePurchase(purchase)
                   handleConsumedPurchases(purchase)
                }
            } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
                // Handle an error caused by a user cancelling the purchase flow.
            } else {
                // Handle any other error codes.
            }
        }

    private fun handleConsumedPurchases(purchase: Purchase) {
        Log.d("TAG_INAPP", "handleConsumablePurchasesAsync foreach it is $purchase")
        val params =
            ConsumeParams.newBuilder().setPurchaseToken(purchase.purchaseToken).build()
        billingClient?.consumeAsync(params) { billingResult, purchaseToken ->
            when (billingResult.responseCode) {
                BillingClient.BillingResponseCode.OK -> {
                    // Update the appropriate tables/databases to grant user the items
                    Log.d(
                        "TAG_INAPP",
                        " Update the appropriate tables/databases to grant user the items"
                    )
                }
                else -> {
                    Log.w("TAG_INAPP", billingResult.debugMessage)
                }
            }
        }
    }

    private fun handleNonConcumablePurchase(purchase: Purchase) {
        Log.v("TAG_INAPP", "handlePurchase : ${purchase}")
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            if (!purchase.isAcknowledged) {
                val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
                    .setPurchaseToken(purchase.purchaseToken).build()
                billingClient?.acknowledgePurchase(acknowledgePurchaseParams) { billingResult ->
                    val billingResponseCode = billingResult.responseCode
                    val billingDebugMessage = billingResult.debugMessage

                    Log.v("TAG_INAPP", "response code: $billingResponseCode")
                    Log.v("TAG_INAPP", "debugMessage : $billingDebugMessage")

                }
            }
        }
    }

    override fun onPurchasesUpdated(p0: BillingResult, p1: MutableList<Purchase>?) {

    }


}