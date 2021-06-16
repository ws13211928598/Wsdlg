package com.example.wsdlg.view.main_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.*
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.core.PoiItem
import com.amap.api.services.geocoder.GeocodeResult
import com.amap.api.services.geocoder.GeocodeSearch
import com.amap.api.services.geocoder.RegeocodeResult
import com.amap.api.services.poisearch.PoiResult
import com.amap.api.services.poisearch.PoiSearch
import com.example.mylibrary.assist.WsViewHolder
import com.example.mylibrary.utils.WsRecyclerAdapterSingle
import com.example.wsdlg.MainActivity
import com.example.wsdlg.base.BaseFragment
import com.example.wsdlg.utils.delegateutil.viewBinding
import com.example.wsdlg.utils.toast
import gongren.com.dlg.R
import gongren.com.dlg.databinding.FragmentHomeBinding
import gongren.com.dlg.databinding.ItemOrderHomeBinding


class HomeFragment : BaseFragment<FragmentHomeBinding>(), GeocodeSearch.OnGeocodeSearchListener,
    AMap.OnMarkerDragListener, PoiSearch.OnPoiSearchListener {
    private var aMap: AMap? = null
    private var locationMarker: Marker? = null
    private var currLatLng: LatLng? = null
    lateinit var marker: Marker
    lateinit var latLonPoint:LatLonPoint
    lateinit var poiSearch:PoiSearch
    var city = "北京"
    var frequency= 0

    override fun getBinding(inflater: LayoutInflater, viewGroup: ViewGroup?): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater, viewGroup, false)
    }

    override fun initView() {
        initmap()
        initRecycler()

    }

    private fun initRecycler() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)
        binding.recyclerView.adapter = object :WsRecyclerAdapterSingle((context)!!, R.layout.item_order_home){
            override fun WsBindViewHolder(holder: WsViewHolder, position: Int) {
                holder.viewBinding(ItemOrderHomeBinding::bind).apply {
                    tvTitle.text = "asdws"
                }
            }

            override fun WsItemCount(): Int {
                return 2
            }

        }
    }


    private fun initmap() {
        val mainActivity = activity as MainActivity
        val myLocationStyle = MyLocationStyle()//设置定位蓝点的Style


        binding.map.onCreate(mainActivity.savedInstance)
        aMap ?: run { aMap = binding.map.map }

        //myLocationStyle.interval(2000)//连续定位间隔
        aMap?.let {
            it.moveCamera(CameraUpdateFactory.zoomTo(13f))
            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//定位一次，且将视角移动到地图中心点。
            //myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。
            it.myLocationStyle = myLocationStyle//设置定位蓝点的Style
            it.uiSettings.isMyLocationButtonEnabled = true//设置默认定位按钮是否显示，非必需设置。
            it.isMyLocationEnabled = true// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false
            it.setOnMyLocationChangeListener {

               /* if (frequency<=0){
                    val latLng = LatLng(it.latitude, it.longitude)
                    marker = aMap!!.addMarker(MarkerOptions().position(latLng))
                    marker.isDraggable = true
                    frequency++
                }*/
                aMap!!.setOnMarkerDragListener(this)
            }
        }


    }

    override fun initListener() {
        binding.btnService.setOnClickListener {
            navigateRegister()
        }
        binding.btnHire.setOnClickListener {
            navigateRegister()

        }

    }

    private fun navigateRegister() {
        if (MainActivity.mainViewModel.mediatorLiveData.value!!.login) {

        } else {
            "请先登录".toast()
            MainActivity.navController?.navigate(R.id.action_mainFragment_to_registerFragment)
        }
    }

    override fun initViewModel() {

    }

    override fun initRequest() {

    }

    override fun initData() {

    }

    override fun onResume() {
        super.onResume()
        binding.map.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.map.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        binding.map.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.map.onSaveInstanceState(outState)
    }

    override fun onRegeocodeSearched(p0: RegeocodeResult?, p1: Int) {//经纬度转坐标
        if (p1 ==1000){

        }
    }

    override fun onGeocodeSearched(p0: GeocodeResult?, p1: Int) {

    }

    override fun onMarkerDragStart(p0: Marker?) {

    }

    override fun onMarkerDrag(p0: Marker?) {

    }

    //拖拽结束
    override fun onMarkerDragEnd(p0: Marker?) {
        var position = p0?.position
         latLonPoint =LatLonPoint(position!!.latitude, position.longitude)
        val query = PoiSearch.Query("", "", city)
        query.pageSize = 10
        query.pageNum = 1  //查询页码
        poiSearch = PoiSearch(context, query)
        poiSearch.setOnPoiSearchListener(this)
        poiSearch.bound =PoiSearch.SearchBound(latLonPoint,1000)//搜索点,半径
        poiSearch.searchPOIAsyn()//发送请求
    }

    //获取查询后的数据
    override fun onPoiSearched(result: PoiResult?, p1: Int) {
        val pois = result?.pois
        pois?.forEach {
            Log.d("asdws2", "onPoiSearched: "+it.adName+it.title)
        }

    }

    override fun onPoiItemSearched(p0: PoiItem?, p1: Int) {

    }

}