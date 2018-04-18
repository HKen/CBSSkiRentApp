/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.smallpay.app.ski.rent.data.remote;


import com.smallpay.app.ski.rent.BuildConfig;


public final class ApiEndPoint {

    public static final String PART_URL = BuildConfig.DEBUG ? "cbs_skat/" : "apis/";

    public static final String ENDPOINT_ACCOUNT_LOGIN = PART_URL + "account/login/";

    public static final String ENDPOINT_CARDS_GET_CUSTOMER_INFO = PART_URL + "cards/get_customer_info/%s";

    public static final String ENDPOINT_ORDERS_CARDCURRENT_EXPENSE = PART_URL + "orders/cardCurrentExpense/%s";

    public static final String ENDPOINT_RENT_FETCH_RENT_GOODS = PART_URL + "rent/fetch_rent_goods/%s";
    public static final String ENDPOINT_RENT_FETCH_RENT_MESSAGE = PART_URL + "rent/fetch_rent_message/%s";
    public static final String ENDPOINT_RENT_RENT_GOODS = PART_URL + "rent/rent_goods/?sessionid=%s";
    public static final String ENDPOINT_RENT_RETURN_GOODS = PART_URL + "rent/return_rent_goods/?sessionid=%s";
    public static final String ENDPOINT_RENT_COMPENSATE_GOODS = PART_URL + "rent/compensate_rent_goods/?sessionid=%s";

    private ApiEndPoint() {
        // This class is not publicly instantiable
    }
}
