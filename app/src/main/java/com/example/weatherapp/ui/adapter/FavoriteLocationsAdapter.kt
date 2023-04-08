package com.example.weatherapp.ui.adapter

import android.content.Context
import android.location.Address
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.data.source.remote.response.WeatherOneCallResponse
import com.example.weatherapp.databinding.FavoriteLocationItemBinding
import com.example.weatherapp.viewmodel.WeatherInfoViewModel

class FavoriteLocationsAdapter(
    private var favoriteLocationsList: MutableList<WeatherOneCallResponse>,
    private val context: Context,
    private val weatherInfoViewModel: WeatherInfoViewModel,
    private var locationsAddresses: MutableList<Address?>
) : RecyclerView.Adapter<FavoriteLocationsAdapter.CustomViewHolder>() {


    class CustomViewHolder(var binding: FavoriteLocationItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding: FavoriteLocationItemBinding = DataBindingUtil
            .inflate(
                LayoutInflater.from(parent.context),
                R.layout.favorite_location_item,
                parent,
                false
            )
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        displayLocationAddress(holder,locationsAddresses, position)
        handleMoreVertButtonClick(holder, position)
    }

    override fun getItemCount(): Int {
        return favoriteLocationsList.size
    }

    private fun displayLocationAddress(
        holder: CustomViewHolder,
        locationsAddresses: MutableList<Address?>,
        position: Int
    ) {
        if (locationsAddresses.size != 0) {
            var address = ""
            if (locationsAddresses?.get(position)?.subAdminArea?.isNotEmpty() == true
                && locationsAddresses?.get(position)?.subAdminArea?.isNotBlank() == true
            ) {
                address = "${locationsAddresses?.get(position)?.subAdminArea ?: ""}\n"
            }

            address += "${locationsAddresses?.get(position)?.adminArea ?: ""}, "
            address += locationsAddresses?.get(position)?.countryName ?: ""

            holder.binding.tvFavoriteLocationItemName.apply {
                this.text = address
            }
        }
    }

    private fun handleMoreVertButtonClick(holder: CustomViewHolder, position: Int) {
        holder.binding.ibFavoriteLocationItemMoreVert.setOnClickListener {
            val popUpMenu = PopupMenu(context, holder.binding.ibFavoriteLocationItemMoreVert)
            popUpMenu.inflate(R.menu.favorite_location_more_vert_menu)
            popUpMenu.setOnMenuItemClickListener {
                if (it.itemId == R.id.action_remove_from_favorite_locations) {
                    displayRemovalAlertDialog(position)
                }
                return@setOnMenuItemClickListener true
            }
            popUpMenu.show()
        }
    }

    private fun displayRemovalAlertDialog(position: Int) {
        AlertDialog
            .Builder(context)
            .setTitle(R.string.alert_dialog_location_removal_title)
            .setMessage(R.string.alert_dialog_location_removal_message)
            .setPositiveButton(R.string.cancel) { _, _ -> /*Do Nothing*/ }
            .setNegativeButton(R.string.remove) { _, _ ->
                weatherInfoViewModel.deleteFavoriteLocation(favoriteLocationsList[position])
                notifyItemRemoved(position)
            }
            .show()
    }

}



