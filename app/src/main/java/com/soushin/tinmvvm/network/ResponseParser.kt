package com.soushin.tinmvvm.network

import okhttp3.Response
import rxhttp.wrapper.annotation.Parser
import rxhttp.wrapper.exception.ParseException
import rxhttp.wrapper.parse.TypeParser
import rxhttp.wrapper.utils.convertTo
import java.io.IOException
import java.lang.reflect.Type


/**
 * 这里用的Rxhttp的自定义Parser 确实强大
 * 学习一下他的想法
 * (https://github.com/liujingxing/okhttp-RxHttp/wiki/%E9%AB%98%E7%BA%A7%E5%8A%9F%E8%83%BD#%E8%87%AA%E5%AE%9A%E4%B9%89Parser)
 * @author created by Soushin
 * @time 2020/1/17 15 24
 */
@Parser(name = "BaseResponse", wrappers = [MutableList::class, BaseResponse::class])
open class ResponseParser<T> : TypeParser<T> {
    /**
     * 此构造方法适用于任意Class对象，但更多用于带泛型的Class对象，如：List<Student>
     *
     * 用法:
     * Java: .asParser(new ResponseParser<List<Student>>(){})
     * Kotlin: .asParser(object : ResponseParser<List<Student>>() {})
     *
     * 注：此构造方法一定要用protected关键字修饰，否则调用此构造方法将拿不到泛型类型
     */
    protected constructor() : super()
    /**
     * 此构造方法仅适用于不带泛型的Class对象，如: Student.class
     *
     * 用法
     * Java: .asParser(new ResponseParser<>(Student.class))   或者  .asResponse(Student.class)
     * Kotlin: .asParser(ResponseParser(Student::class.java)) 或者  .asResponse<Student>()
     */
    constructor(type: Type) : super(type)

    @Throws(IOException::class)
    override fun onParse(response: Response): T {
//        val type = ParameterizedTypeImpl[BaseResponse::class.java, mType]

        val data: BaseResponse<T> = response.convertTo(BaseResponse::class.java,*types)
        if (data.getData() == null && types[0] === String::class.java) {
            /*
             * 考虑到有些时候服务端会返回：{"errorCode":0,"errorMsg":"关注成功"}  类似没有data的数据
             * 此时code正确，但是data字段为空，直接返回data的话，会报空指针错误，
             * 所以，判断泛型为String类型时，重新赋值，并确保赋值不为null
             */
            @Suppress("UNCHECKED_CAST")
            data.setData( data.getMessage() as T)
        }

        if (!data.isOk() || data.getData() == null) {
            throw ParseException(data.getCode().toString(), data.getMessage(), response)
        }

        return data.getData()!!
    }


}