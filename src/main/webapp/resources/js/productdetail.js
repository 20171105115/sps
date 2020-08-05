$(function () {
    var productId = getQueryString("productId");

    var getProductDetailUrl = '/sps/productadmin/getproductdetail?productId='+productId;

    $.getJSON(getProductDetailUrl,function (data) {
        if (data.success){
            var product = data.product;
            $('#product-img').attr('src', product.productImg);
            $('#product-price').text(product.productPrice);
            $('#product-desc').text(product.productDesc);
            $('#user-name').text(product.owner.name);
            $('#user-phone').text(product.owner.phone);
            $('#product-time').text(
                new Date(product.lastEditTime).Format("yyyy-MM-dd"));
            var imgListHtml = '';
            product.productImgList.map(function(item, index) {
                imgListHtml += '<div> <img src="' + item.imgAddr
                    + '" width="100%" /></div>';
            });
            $('#imgList').html(imgListHtml);
        }
    })
})