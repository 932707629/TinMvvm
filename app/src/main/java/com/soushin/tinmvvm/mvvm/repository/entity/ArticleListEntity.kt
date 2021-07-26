package com.soushin.tinmvvm.mvvm.repository.entity

data class ArticleListEntity(
    var curPage: Int? = null, // 1
    var datas: List<Article>? = null,
    var offset: Int? = null, // 0
    var over: Boolean? = null, // false
    var pageCount: Int? = null, // 544
    var size: Int? = null, // 20
    var total: Int? = null // 10869
)

data class Article(
    var id: Int? = null, // 19076
    var title: String? = null, // Flutter 命令本质之 Flutter tools 机制源码深入分析
)
