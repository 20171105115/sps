$(function () {

    var getProductListUrl = '/sps/productadmin/getproductlist';

    var getCategoryUrl = '/sps/productadmin/getcategoryandarea';

    var productCategoryId = '';

    var productName = '';
    //默认的页码
    var pageIndex = 1;
    //每页的条数
    var pageSize = 100;

    var isMine = 0;

    getCategory();

    function getCategory() {
        $.getJSON(getCategoryUrl, function (data) {
            if (data.success) {
                var html = '';
                data.productCategoryList.map(function (item, index) {
                    html += '<a href="#" class="button" data-id="' + item.productCategoryId + '">'
                        + item.productCategoryName + '</a>'
                })
                $('#shoplist-search-div').html(html);
            }
        })
    }

    getProductList();

    function getProductList() {
        var productStr = '?productName=' + productName + '&productCategoryId='
            + productCategoryId + '&pageIndex=' + pageIndex + '&pageSize=' + pageSize + '&isMine=' + isMine;
        var url = getProductListUrl + productStr;
        $.getJSON(url, function (data) {
            if (data.success) {
                var html = '';
                data.productList.map(function (item, index) {
                    html += '' + '<div class="card" data-id='
                        + item.productId + '>'
                        + '<div class="card-header">' + item.productName
                        + '</div>' + '<div class="card-content">'
                        + '<div class="list-block media-list">' + '<ul>'
                        + '<li class="item-content">'
                        + '<div class="item-media">' + '<img src="'
                        + item.productImg + '" width="44">' + '</div>'
                        + '<div class="item-inner">'
                        + '<div class="item-subtitle">' + item.productDesc
                        + '</div>' + '</div>' + '</li>' + '</ul>'
                        + '</div>' + '</div>' + '<div class="card-footer">'
                        + '<p class="color-gray">'
                        + new Date(item.lastEditTime).Format("yyyy-MM-dd")
                        + '更新</p>' + '<span>点击查看</span>' + '</div>'
                        + '</div>';
                });
                $('.shop-list').html(html)
            }
        })
    };

    $('.shoplist-button-div').on('click', '.button', function (e) {
        productCategoryId = e.target.dataset.id;
        if ($(e.target).hasClass('button-fill')) {
            $(e.target).removeClass('button-fill');
            productCategoryId = '';
        } else {
            $(e.target).addClass('button-fill').siblings()
                .removeClass('button-fill');
        }
        $('.list-div').empty();
        if (isMine==1){
            $('#me').click();
        }
        getProductList();
    });

    // 点击店铺的卡片进入该店铺的详情页
    $('.shop-list').on('click', '.card', function (e) {
        var productId = e.currentTarget.dataset.id;
        window.location.href = '/sps/productadmin/productdetail?productId=' + productId;
    });




    $('#me').click(function () {
        isMine = 1;
        var url = '/sps/productadmin/getproductlist?pageIndex=1&pageSize=100&isMine=' + isMine;
        $.getJSON(url, function (data) {
            if (data.success) {
                $('#shoplist-search-div').empty();
                var html = '<div class="row button" style="margin-bottom: 3px">'
                    + '<div class="col-50">商品名称</div>'
                    + '<div class="col-25">修改</div>'
                    + '<div class="col-25">删除</div>'
                    + '</div>';
                data.productList.map(function (item, index) {
                    html += '<div class="row button" style="margin-bottom: 3px">'
                        + '<div class="col-50">' + item.productName+ '</div>'
                        + '<div class="col-25"><a rel="external" href="/sps/productadmin/productoperation?productId='+ item.productId +'" data-ajax="false" >修改</a></div>'
                        + '<div class="col-25"><a href="#" class="delete" data-id="'+ item.productId +'">删除</a>'+'</div></div>'
                });
                html += '<div class="row button">'
                + '<div class="col-100"><a rel="external" href="/sps/productadmin/productoperation" data-ajax="false" >添加商品</a></div>';
                $('.shop-list').html(html);
            }

        });

    });

    $('.row').on('click','.delete',function (e) {
        var productId = e.currentTarget.data.id;
        var url = '/sps/productadmin/removeproduct?productId='+productId;
        $.getJSON(url,function (data) {
            if (data.success){
                $.toast("删除成功");
            } else {
                $.toast("删除失败");
            }
        });
    })



    // 需要查询的店铺名字发生变化后，重置页码，清空原先的店铺列表，按照新的名字去查询
    // $('#search').on('change', function(e) {
    //     shopName = e.target.value;
    //     $('.list-div').empty();
    //     pageNum = 1;
    //     addItems(pageSize, pageNum);
    // });




})