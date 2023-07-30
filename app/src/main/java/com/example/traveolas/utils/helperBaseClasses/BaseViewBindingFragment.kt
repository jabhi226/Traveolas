package com.example.traveolas.utils.helperBaseClasses


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

/**
 * Base application `Fragment` class with overridden [onCreateView] that inflates the view
 * based on the [VB] type argument and set the [_binding] property.
 *
 * @param VB The type of the View Binding class.
 */
open class BaseViewBindingFragment<VB : ViewBinding> : Fragment() {

    /** The view binding instance. */
    var _binding: VB? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return createBindingInstance(inflater, container).also { _binding = it }.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /** Creates new [VB] instance using reflection. */
    @Suppress("UNCHECKED_CAST")
    open fun createBindingInstance(inflater: LayoutInflater, container: ViewGroup?): VB {
        val vbType = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
        val vbClass = vbType as Class<VB>
        val method = vbClass.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )

        // Call VB.inflate(inflater, container, false) Java static method
        return method.invoke(null, inflater, container, false) as VB
    }
}