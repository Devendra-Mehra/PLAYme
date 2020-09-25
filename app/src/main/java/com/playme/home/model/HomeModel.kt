package com.playme.home.model

import com.playme.core.PersistenceContract
import com.playme.home.utils.HomeContract
import javax.inject.Inject

class HomeModel @Inject constructor(
    val repository: HomeContract.Repository,
    val persistence: PersistenceContract
) {


}