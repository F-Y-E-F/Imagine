package com.example.imagine.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.imagine.R
import com.example.imagine.screens.adapters.PhotosRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_photos.*


class PhotosFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_photos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val photos = ArrayList<String>()
       /* photos.add("https://cdn.pixabay.com/photo/2018/01/05/16/24/rose-3063284_150.jpg")
        photos.add("https://cdn.pixabay.com/photo/2018/01/28/11/24/sunflower-3113318_150.jpg")
        photos.add("https://cdn.pixabay.com/photo/2018/04/05/14/09/sunflower-3292932_150.jpg")
        photos.add("https://cdn.pixabay.com/photo/2017/05/08/13/15/spring-bird-2295434_150.jpg")
        photos.add("https://cdn.pixabay.com/photo/2016/07/12/18/54/lily-1512813_150.jpg")
        photos.add("https://cdn.pixabay.com/photo/2015/04/10/00/41/yellow-715540_150.jpg")
        photos.add("https://cdn.pixabay.com/photo/2017/01/11/17/27/drip-1972411_150.jpg")
        photos.add("https://cdn.pixabay.com/photo/2015/08/13/20/06/flower-887443_150.jpg")*/

        photos.add("https://pixabay.com/get/5ee8d2474e51b108f5d08460962934771437d9e6554c704f75267dd59649c65c_1280.jpg")
        photos.add("https://pixabay.com/get/57e5d6454a5aa414f6da8c7dda79367d1c3ed6e250576c48732f72d2964cc15bbc_1280.jpg")
        photos.add("https://pixabay.com/get/53e8d3424b5ba414f6da8c7dda79367d1c3ed6e250576c48732f72d2964ccc51b1_1280.jpg")
        photos.add("https://pixabay.com/get/5ee8d2474e51b108f5d08460962934771437d9e6554c704f75267dd59649c65c_1280.jpg")
        photos.add("https://pixabay.com/get/57e5d6454a5aa414f6da8c7dda79367d1c3ed6e250576c48732f72d2964cc15bbc_1280.jpg")
        photos.add("https://pixabay.com/get/53e8d647425ba514f6da8c7dda79367d1c3ed6e250576c48732f72d2964ccc51b1_1280.jpg")
        photos.add("https://pixabay.com/get/57e5d6454a5aa414f6da8c7dda79367d1c3ed6e250576c48732f72d2964cc15bbc_1280.jpg")
        photos.add("https://pixabay.com/get/57e6d7444b5baf14f6da8c7dda79367d1c3ed6e250576c48732f72d2964cc15bbc_1280.jpg")
        photos.add("https://pixabay.com/get/53e8d34a4a57ae14f6da8c7dda79367d1c3ed6e250576c48732f72d2964ac75ab8_1280.jpg")
        photos.add("https://pixabay.com/get/53e8d34a4a56a514f6da8c7dda79367d1c3ed6e250576c48732f72d2964ac75ab8_1280.jpg")
        photos.add("https://pixabay.com/get/53e8d34a4d56a814f6da8c7dda79367d1c3ed6e250576c48732f72d2964ac75ab8_1280.jpg")
        photos.add("https://pixabay.com/get/53e8d34a4c5ba914f6da8c7dda79367d1c3ed6e250576c48732f72d2964ac75ab8_1280.jpg")
        photos.add("https://pixabay.com/get/53e8d3444a53a514f6da8c7dda79367d1c3ed6e250576c48732f72d2964ac75ab8_1280.jpg")
        photos.add("https://pixabay.com/get/53e8d3464c56a914f6da8c7dda79367d1c3ed6e250576c48732f72d2964ac75ab8_1280.jpg")
        photos.add("https://pixabay.com/get/53e8d3454a51ac14f6da8c7dda79367d1c3ed6e250576c48732f72d2964ac75ab8_1280.jpg")

        /*photos.add("https://pixabay.com/get/57e9d2414e53ad14f1dc8460962934771437d9e6554c704f75267ed59e4fc050_640.jpg")
        photos.add("https://pixabay.com/get/57e9d2414e53ad14f1dc8460962934771437d9e6554c704f75267ed59e4fc050_640.jpg")
        photos.add("https://pixabay.com/get/57e9d2414e53ad14f1dc8460962934771437d9e6554c704f75267ed59e4fc050_640.jpg")
        photos.add("https://pixabay.com/get/57e9d2414e53ad14f1dc8460962934771437d9e6554c704f75267ed59e4fc050_640.jpg")
        photos.add("https://pixabay.com/get/57e9d2414e53ad14f1dc8460962934771437d9e6554c704f75267ed59e4fc050_640.jpg")*/

        photosRecyclerView.apply {
             layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            adapter = PhotosRecyclerViewAdapter(photos)
        }
    }


}