package com.example.android.swapi.utilities

import android.widget.ImageButton
import android.widget.TextView
import androidx.databinding.BindingAdapter

/**
 *
 * @author Cole Michaels
 * @email cmichaelsd@gmail.com
 * @date 10/23/20
 *
 */
@BindingAdapter("name")
fun name(view: TextView, name: String) {
    view.text = "Name: $name"
}
@BindingAdapter("height")
fun height(view: TextView, height: String) {
    view.text = "Height: $height"
}
@BindingAdapter("mass")
fun mass(view: TextView, mass: String) {
    view.text = "Mass: $mass"
}
@BindingAdapter("hairColor")
fun hairColor(view: TextView, hairColor: String) {
    view.text = "Hair Color: $hairColor"
}
@BindingAdapter("skinColor")
fun skinColor(view: TextView, skinColor: String) {
    view.text = "Skin Color: $skinColor"
}
@BindingAdapter("eyeColor")
fun eyeColor(view: TextView, eyeColor: String) {
    view.text = "Eye Color: $eyeColor"
}
@BindingAdapter("birthYear")
fun birthYear(view: TextView, birthYear: String) {
    view.text = "Birth Year: $birthYear"
}
@BindingAdapter("gender")
fun gender(view: TextView, gender: String) {
    view.text = "Gender: $gender"
}
@BindingAdapter("favorite")
fun favorite(view: ImageButton, favorite: Boolean) {
    if (favorite) {
        view.setImageResource(android.R.drawable.btn_star_big_on)
    } else {
        view.setImageResource(android.R.drawable.btn_star_big_off)
    }
}