$(function () {

    var productId = getQueryString("productId");
    //获取分类和区域信息
    var getCategoryAndAreaUrl = '/sps/productadmin/getcategoryandarea';

    var addProductUrl = '/sps/productadmin/addproduct';

    var getProductInfoUrl = '/sps/productadmin/getproductinfo?productId=' + productId;

    var modifyProductUrl = '/sps/productadmin/modifyproduct';

    if (productId) {
        getProductInfo();//修改
    } else {
        getCategoryAndArea();//注册
    }

    function getProductInfo() {
        $.getJSON(getProductInfoUrl, function (data) {
            if (data.success) {
                var product = data.product;
                $('#product-name').val(product.productName);
                $('#product-desc').val(product.productDesc);
                $('#product-price').val(product.productPrice);
                // 获取原本的商品类别以及该店铺的所有商品类别列表
                var optionHtml = '';
                var optionArr = data.productCategoryList;
                var optionSelected = product.productCategory.productCategoryId;
                // 生成前端的HTML商品类别列表，并默认选择编辑前的商品类别
                optionArr
                    .map(function (item, index) {
                        var isSelect = optionSelected === item.productCategoryId ? 'selected'
                            : '';
                        optionHtml += '<option data-id="'
                            + item.productCategoryId
                            + '"'
                            + isSelect
                            + '>'
                            + item.productCategoryName
                            + '</option>';
                    });
                $('#product-category').html(optionHtml);
            }
        });
    }


    function getCategoryAndArea() {
        $.getJSON(getCategoryAndAreaUrl, function (data) {
            if (data.success) {
                var categoryHtml = ''
                data.productCategoryList.map(function (item, index) {
                    categoryHtml += '<option data-id="' + item.productCategoryId + '">'
                        + item.productCategoryName + '</option>'
                })

                $('#product-category').html(categoryHtml);
            }
        })
    };

    // 针对商品详情图控件组，若该控件组的最后一个元素发生变化（即上传了图片），
    // 且控件总数未达到6个，则生成新的一个文件上传控件
    $('.detail-img-div').on('change', '.detail-img:last-child', function () {
        if ($('.detail-img').length < 6) {
            $('#detail-img').append('<input type="file" class="detail-img">');
        }
    });

    $('#submit').click(function () {
        var product = {};
        if (productId) {
            product.productId = productId;
        }
        product.productName = $('#product-name').val();
        product.productPrice = $('#product-price').val();
        product.productDesc = $('#product-desc').val();

        product.productCategory = {
            productCategoryId: $('#product-category').find('option').not(function () {
                return !this.selected
            }).data('id')
        };
        var formData = new FormData();

        var thumbnail = $('#small-img')[0].files[0];

        formData.append("thumbnail", thumbnail);

        $('.detail-img').map(
            function (index, item) {
                // 判断该控件是否已选择了文件
                if ($('.detail-img')[index].files.length > 0) {
                    // 将第i个文件流赋值给key为productImgi的表单键值对里
                    formData.append('productImg' + index,
                        $('.detail-img')[index].files[0]);
                }
            });
        formData.append("productStr", JSON.stringify(product));
        // 获取表单里输入的验证码
        var verifyCodeActual = $('#j_captcha').val();
        if (!verifyCodeActual) {
            $.toast('请输入验证码！');
            return;
        }

        formData.append("verifyCodeActual", verifyCodeActual);

        $.ajax({
            url: productId ? modifyProductUrl : addProductUrl,
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                if (data.success) {
                    $.toast('提交成功！');
                    $('#captcha_img').click();
                } else {
                    $.toast('提交失败！');
                    $('#captcha_img').click();
                }
            }
        });

    });


});