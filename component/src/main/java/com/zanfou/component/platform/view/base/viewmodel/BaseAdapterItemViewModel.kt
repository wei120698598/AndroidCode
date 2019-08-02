package com.zanfou.component.platform.view.base.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class BaseAdapterItemViewModel<Model>(context: Application) : AndroidViewModel(context) {
    var model: Model? = null
}