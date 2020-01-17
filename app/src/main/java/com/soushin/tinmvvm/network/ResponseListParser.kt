package com.soushin.tinmvvm.network

import com.soushin.tinmvvm.mvvm.model.entity.BaseResponse
import okhttp3.Response
import rxhttp.wrapper.annotation.Parser
import rxhttp.wrapper.entity.ParameterizedTypeImpl
import rxhttp.wrapper.exception.ParseException
import rxhttp.wrapper.parse.AbstractParser
import java.lang.reflect.Type


/**
 * 这里用的Rxhttp的自定义Parser 确实强大
 * (https://github.com/liujingxing/okhttp-RxHttp/wiki/%E9%AB%98%E7%BA%A7%E5%8A%9F%E8%83%BD#%E8%87%AA%E5%AE%9A%E4%B9%89Parser)
 * @author created by Soushin
 * @time 2020/1/17 15 24
 */
@Parser(name = "BaseListResponse")
class ResponseListParser<T> : AbstractParser<MutableList<T>> {

    constructor():super()

    constructor(type: Type):super(type)

    override fun onParse(response: Response): MutableList<T> {
        val type=ParameterizedTypeImpl.get(BaseResponse::class.java, MutableList::class.java,mType)

        val data=convert<BaseResponse<MutableList<T>>>(response,type)

        if (!data.isOk()||data.data==null){
            throw ParseException(data.errorCode.toString(),data.errorMsg,response)
        }
        return data.data!!
    }


}