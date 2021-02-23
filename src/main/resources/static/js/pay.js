(function (){
	document.write('<script src="../static/js/jquery.min.js"><\/script>')
	document.write('<script src="../static/js/constance.js"><\/script>')

	var clientHeight = document.documentElement.clientHeight;
	var headerH = document.getElementById("header").offsetHeight;
	var shopbar = document.getElementById("shopbar").offsetHeight;
	var contentH = clientHeight - headerH - shopbar;
	$(".order-content").css("height", contentH);
	var orderId = localStorage.getItem("orderid")

	inithead();
	initgoods();

	function inithead() {
		var header = '<div onClick="javascript :history.back(-1);"><</div>' + '<div class="title">订单结算</div>';
		$(".header").append(header);
	}

	function initgoods() {
		var itemTpl = '<div class="content">' + '<img src="$imgsrc" alt="" class="img">' + '<div class="detail">' + '<div class="proname">商品：$productname</div>' + '<div class="description">描述：$description</div>' + '<div class="price">价格：￥$min_price</div>' + '<div class="numandtotal">' + '<div class="num">数量：x$chooseCount</div>' + '<div class="total">￥$totalPrice</div>' + '</div>' + '</div>' + '</div>';
		var total = 0;
		$.get("http://172.11.1.94:4396/order/orderById/" + orderId, function(ret) {
			ret.data.forEach(function(item, index) {
				total = item.total;
				var str = itemTpl
					.replace("$imgsrc", item.picture)
					.replace("$productname", item.name)
					.replace("$description", item.description)
					.replace("$min_price", item.min_price)
					.replace("$chooseCount", item.productCount)
					.replace("$totalPrice", item.min_price * item.productCount);
				$(".order-content").append(str);
			})
			initshopbar(ret.data[0].total);
		})

	}

	function initshopbar(total) {
		var itemtpl = '<div class="totalPrice">' + '<div class="zongji">总计：￥$total</div>' + '</div>' + '<div class="definedpay">确认支付</div>';
		var str = itemtpl.replace("$total", total);
		$(".shopbar").append(str);
	}


	$(".definedpay").on("click", function(e) {
		var totalAmount = $(e.currentTarget).parent("").find(".zongji").text().split("￥")[1]
		var subject = "茶楼消费";
		var data = {
			subject : subject,
			outTradeNo : orderId,
			totalAmount : totalAmount
		}

		$.ajax({
			url : baseUrl.url + "alipay/pay",
			data : JSON.stringify(data),
			contentType : "application/json",
			type : "POST",
			success : function(data) {
				//调用支付接口
				// var aliPayPlus = api.require('aliPayPlus');
				// aliPayPlus.payOrder({
				// 	orderInfo : data.msg,
				// 	sandbox : true,
				// }, function(ret, err) {
				// 	api.alert({
				// 		title : '支付结果',
				// 		msg : ret.code,
				// 		buttons : ['确定']
				// 	});
				// });
			},
		});

	})
	//这段代码放在所有的样式文件之前，设置html根元素的fontSize
	var docEl = document.documentElement;

	function setRem() {
		// 这个10不是固定的，只是计算出来的rem要和cssrem插件setting中设置的37.5保持一致
		// iphone6设备宽度是375，因此基准值刚好是10
		var rem = docEl.clientWidth / 10;
		//获取基准值
		docEl.style.fontSize = rem + "px";
		//动态设置html根元素的fontSize
	}

	setRem();

	window.addEventListener("resize", setRem);

	//用户每次浏览页面的时候， 就会触发pagesshow方法（有兼容性问题）
	window.addEventListener("pageshow", function(e) {
		// 使用e.presisted就是判断当前页面是不是在缓存中加载
		// 如果缓存中加载（就是为true的时候），重新设置rem
		if (e.persisted) {
			setRem();
		}
	});
})()
