package com.nesgorilla.manager

import android.content.Context
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.nesgorilla.R

/**
 * Resource Manager Singleton
 * @author n.diazgranados
 *
 * This class is responsible to search any resource and also all logic related for doing it.
 *
 * Context could be conveniently used with an specific context, otherwise will use application context.
 */
class ResourceManager private constructor(private val appContext: Context) {

    enum class ResourceType(val value: String) {
        STRING("string"),
        ARRAY("array"),
        LAYOUT("layout"),
        ID("id")
    }

    fun getResources(context: Context = appContext) =
        context.resources

    fun getIdentifier(
        resourceType: ResourceType,
        identifierName: String,
        defaultValue: Int = NO_IDENTIFIER,
        context: Context = appContext
    ) =
        try {
            getResources(context).getIdentifier(
                identifierName,
                resourceType.value,
                context.packageName
            )
        } catch (e: Exception) {
            defaultValue
        }

    @JvmOverloads
    fun getStringArray(
        @ArrayRes idArrayRes: Int,
        defaultValue: String = RESOURCE_NOT_FOUND,
        context: Context = appContext
    ) =
        try {
            getResources(context).getStringArray(idArrayRes).toList()
        } catch (e: Exception) {
            listOf(defaultValue)
        }


    @JvmOverloads
    fun getString(
        @StringRes idStringRes: Int,
        defaultValue: String = RESOURCE_NOT_FOUND,
        context: Context = appContext
    ) =
        try {
            getResources(context).getString(idStringRes)
        } catch (e: Exception) {
            defaultValue
        }

    @JvmOverloads
    fun getDrawable(
        @DrawableRes idDrawableRes: Int?,
        @DrawableRes defaultValue: Int = R.drawable.ic_launcher_background,
        context: Context = appContext
    ) =
        try {
            idDrawableRes?.let {
                ResourcesCompat.getDrawable(
                    getResources(context),
                    idDrawableRes,
                    null
                )
            }
                ?: ResourcesCompat.getDrawable(getResources(context), defaultValue, null)
        } catch (e: Exception) {
            ResourcesCompat.getDrawable(
                getResources(context),
                android.R.drawable.stat_notify_error,
                null
            )
        }

    fun getStringWithParams(
        @StringRes idStringRes: Int,
        vararg params: Any
    ) =
        try {
            getResources(appContext).getString(idStringRes, *params)
        } catch (e: Exception) {
            RESOURCE_NOT_FOUND
        }

    @JvmOverloads
    fun getColor(
        @ColorRes idColorRes: Int,
        defaultValue: Int = NO_IDENTIFIER,
        context: Context = appContext
    ) =
        try {
            ContextCompat.getColor(context, idColorRes)
        } catch (e: Exception) {
            defaultValue
        }

    @JvmOverloads
    fun getDimension(
        @DimenRes resourceId: Int,
        context: Context = appContext
    ) =
        getResources(context).getDimension(resourceId)

    @JvmOverloads
    fun getInteger(
        @IntegerRes resourceId: Int,
        defaultValue: Int = NO_IDENTIFIER,
        context: Context = appContext
    ) =
        try {
            getResources(context).getInteger(resourceId)
        } catch (e: Exception) {
            defaultValue
        }

    fun getQuantityString(
        @PluralsRes resourceId: Int,
        quantity: Int,
        arrayOfAny: Any
    ) =
        try {
            (appContext).resources.getQuantityString(resourceId, quantity, arrayOfAny)
        } catch (e: Exception) {
            RESOURCE_NOT_FOUND
        }

    @JvmOverloads
    fun getStringResourceId(
        identifierName: String,
        defaultValue: Int = NO_IDENTIFIER,
        context: Context = appContext
    ) =
        getIdentifier(ResourceType.STRING, identifierName, defaultValue, context)


    @JvmOverloads
    fun getArrayResourceId(
        identifierName: String,
        defaultValue: Int = NO_IDENTIFIER,
        context: Context = appContext
    ) =
        getIdentifier(ResourceType.ARRAY, identifierName, defaultValue, context)

    @JvmOverloads
    fun getLayoutResourceId(
        identifierName: String,
        defaultValue: Int = NO_IDENTIFIER,
        context: Context = appContext
    ) =
        getIdentifier(ResourceType.LAYOUT, identifierName, defaultValue, context)


    @JvmOverloads
    fun getViewResourceId(
        identifierName: String,
        defaultValue: Int = NO_IDENTIFIER,
        context: Context = appContext
    ) =
        getIdentifier(ResourceType.ID, identifierName, defaultValue, context)

    /**
     * Error codes managed in the hourly app are contained in String resources.
     * @return the id of the string resource
     */
    @JvmOverloads
    fun getErrorCodeDisplayMessage(
        errorCode: Int,
        defaultValue: String = RESOURCE_NOT_FOUND,
        context: Context = appContext
    ) =
        getString(getErrorMessageResId(errorCode, context = context), defaultValue, context)

    /**
     * Error codes managed in the hourly app are contained in String resources.
     * @return the id of the string resource
     */
    @JvmOverloads
    fun getErrorMessageResId(
        errorCode: Int,
        defaultValue: Int = NO_IDENTIFIER,
        context: Context = appContext
    ) =
        getStringResourceId(PREFIX_ERROR_CODE_IDENTIFIER.plus(errorCode), defaultValue, context)

    /**
     * Error codes managed in the hourly app are contained in String resources.
     * @return the id of the string resource
     */
    @JvmOverloads
    fun getErrorTitleResId(
        errorCode: Int,
        defaultValue: Int = NO_IDENTIFIER,
        context: Context = appContext
    ) =
        getStringResourceId(PREFIX_ERROR_TITLE_IDENTIFIER.plus(errorCode), defaultValue, context)

    companion object {

        private const val PREFIX_ERROR_CODE_IDENTIFIER = "error_code_"
        private const val PREFIX_ERROR_TITLE_IDENTIFIER = "title_error_code_"
        private const val RESOURCE_NOT_FOUND = "RESOURCE_NOT_FOUND"
        private const val NO_IDENTIFIER = -1

        @Volatile
        private var instance: ResourceManager? = null

        @JvmStatic
        fun getInstance(appContext: Context): ResourceManager {
            return instance ?: synchronized(this) {
                instance ?: ResourceManager(appContext).also { instance = it }
            }
        }
    }
}