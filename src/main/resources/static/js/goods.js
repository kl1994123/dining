//注意：将方法暴露到全局，不能用apicloud的apiready
(function() {
	document.write('<script src="../static/js/jquery.min.js"><\/script>')
	document.write('<script src="../static/js/constance.js"><\/script>')
	window.shopBar = {};
	initShopBar();
	bindTable();
		getList();
		var clientHeight = document.documentElement.clientHeight;
		//视窗高度
		var scrollHeight = document.body.scrollHeight;
		//body滚动过的总长
		var scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
		//body滚动过的总长
		window.addEventListener("scroll", function() {
			var clientHeight = document.documentElement.clientHeight;
			//视窗高度
			var scrollHeight = document.body.scrollHeight;
			//body滚动过的总长
			var scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
			//body滚动过的总长
			var preDis = 30;
			//提前的预值
			if ((scrollTop + clientHeight) >= (scrollHeight - preDis)) {
				//自定义页面，一次最多滚动3页
				if (page < 3) {
					if (isLoading)
						return;
					getList();
				} else {
					$(".loading").text("加载完成");
				}
			}
		});
	
		//左侧tab点击事件
		$(".menu-inner").on("click", ".left-item", function(e) {
			var $target = $(e.currentTarget);
			//添加点击样式
			$target.addClass("active");
			$target.siblings().removeClass("active");
			//将数据传给右侧详情列表
			initRightContent($target.data("itemData"));
			//加载右侧商品
		})
		//购物车里的加号事件
		$(".choose-content").on("click", ".plus", function(e) {
			var $count = $(e.currentTarget).parent("").find(".count");
			$count.text(parseInt($count.text() || "0") + 1);
			//更新挂载的数据
			var $item = $(e.currentTarget).parents(".choose-item").first();
			var itemData = $item.data("itemData");
	
		})
		//购物车里的减号事件
		$(".choose-content").on("click", ".minus", function(e) {
			var $count = $(e.currentTarget).parent("").find(".count");
			if ($count.text() == 0) {
				//$(e.currentTarget).parent(".select-content").parent(".choose-item").remove(".choose-item");
				return;
			}
			$count.text(parseInt($count.text() || "0") - 1);
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
		initTabBar();
	    $(".back-icon").on("click",function(){
	    	window.history.go(-1);
	    })
})();

function bindTable(){
	var str = window.location.href;
	var tableId = str.split("id=")[1];
	$.ajax({
		data:{
			tableId : tableId
		},
		url:"http://172.11.2.40:4396/table/bindTable",
		success:function (ret){
			if (ret.success) {
				alert(ret.msg)
			}else {
				alert(ret.msg)
			}
		}
	})
}

//更新购物车
function refesshcar() {
	var tpl = '<div class="choose-item">' + '<div class="item-name">$name</div>' + '<div class="price">￥<span class="total">$price</span></div>' + '<div class="select-content">' + '<div class="minus"></div>' + '<div class="count">$chooseCount</div>' + '<div class="plus"></div>' + '</div>' + '</div>';

}

//加载menu页的头部
function initTabBar() {
	//menu页的模板字符串
	var itemTpl = '<a class="$key tab-item" href="../html/$key.html">' + '$text' + '</a>';
	var items = [{
		key : "goods",
		text : "点菜"
	}, {
		key : "comment",
		text : "评价"
	}, {
		key : "restaurant",
		text : "商家"
	}];

	var str = "";
	items.forEach(function(item) {
		str += itemTpl.replace(/\$key/g, item.key)//正则解决全局替换的问题
		.replace("$text", item.text);
	});

	$(".tab-bar").append(str);
	// //判断当前属于哪个页面
	var arr = window.location.pathname.split("/");
	var page = arr[arr.length - 1].replace(".html", "");
	//最后一个元素，去掉.html
	$("a." + page).addClass("active");
}

//获取列表数据
function getList() {
	$.get("http://172.11.1.94:4396/food/food", function(data) {
	// $.get("../json/goods.json", function(data) {
		var itemTpl = '<div class="left-item">' + '<div class="item-text">$getItemContent</div>' + '</div>';
		//		var list = JSON.parse(data).data || [];
		var list = data.data || [];
		//把数据挂载到window对象上，方便其他模块调用
		window.food_spu_tags = list;
		list.forEach(function(item, index) {
			var str = itemTpl.replace("$getItemContent", getItemContent(item));
			var $target = $(str);
			//将item数据挂载到left-item上面
			$target.data("itemData", item);
			$(".left-bar-inner").append($target);
		})
		$(".left-item").first().click();
		//默认显示第一个tab
	})
	//关于图标的处理
	function getItemContent(data) {
		if (data.icon) {
			return '<img class="item-icon" src=' + data.icon + '>' + data.name;
		} else {
			return data.name;
		}
	}
}

//加载右侧商品
function initRightContent(data) {
	//右侧商品的模板字符串
	var itemTpl = '<div class="menu-item" >' + '<img src=$picture class="img">' + '<div class="menu-item-right" >' + '<div class="item-title">$name</div>' + '<div class="item-desc">$description</div>' + '<div class="item-price">￥$min_price<span class="unit">/$unit</span></div>' + '</div>' + '<div class="select-content">' + '<div class="minus"></div>' + '<div class="count">$chooseCount</div>' + '<div class="plus"></div>' + '</div>' + '</div>';
	$(".right-list-inner").html("");
	var list = data.spus || [];
	list.forEach(function(item, index) {
		//默认为0
		if (!item.chooseCount) {
			item.chooseCount = 0;
		}
		var str = itemTpl.replace("$id", item.id).replace("$picture", item.picture).replace("$name", item.name).replace("$description", item.description).replace("$praise_content", item.praiseContent).replace("$min_price", item.minPrice).replace("$unit", item.unit).replace("$chooseCount", item.chooseCount);
		var $target = $(str);
		//把数据挂载在data属性上，方便以后获取
		$target.data("itemData", item);
		$(".right-list-inner").append($target);
	});

	//右侧顶部title
	$(".right-title").text(data.name);

	//加号事件
	$(".menu-item").on("click", ".plus", function(e) {
		var $count = $(e.currentTarget).parent("").find(".count");
		var $goodsname = $(e.currentTarget).parent(".select-content").parent(".menu-item").find(".item-title");
		var $goodsprice = $(e.currentTarget).parent(".select-content").parent(".menu-item").find(".item-price");
		$count.text(parseInt($count.text() || "0") + 1);
		//更新挂载的数据
		var $item = $(e.currentTarget).parents(".menu-item").first();
		var itemData = $item.data("itemData");
		itemData.chooseCount++;
		// 更新购物车
		window.shopBar.renderItems();
	})
	//减号事件
	$(".menu-item").on("click", ".minus", function(e) {
		var $count = $(e.currentTarget).parent("").find(".count");
		if ($count.text() == 0)
			return;
		$count.text(parseInt($count.text() || "0") - 1);

		//更新挂载的数据
		var $item = $(e.currentTarget).parents(".menu-item").first();
		var itemData = $item.data("itemData");
		itemData.chooseCount--;
		// 更新购物车
		window.shopBar.renderItems();
	})
}

//shopbar
function initShopBar() {
	//shopbar的购物车上部分模板字符串
	var itemTopTpl = '<div class="choose-content hide">' + '<div class="content-top">' + '<div class="clear-car"><img src = "../static/images/huishou.svg"></div>' + '</div>' + '</div>';

	//shopbar的购物车下部分模板字符串
	var itemBottomTpl = '<div class="bottom-content">' + '<div class="shop-icon">' + '<div class="dot-num hide"></div>' + '</div>' + '<div class="price-content">' + '<p class="total-price">￥<span class="total-price-span">0</span></p>' +
	//      '<p class="other-price">另需配送&nbsp;￥<span class="shipping-fee">0</span></p>' +
	'</div>' + '<div class="submit-btn">去结算</div>' + '</div>';

	var $strTop = $(itemTopTpl);

	var $strBottom = $(itemBottomTpl);

	$(".shop-bar").append($strTop);
	$(".shop-bar").append($strBottom);

	//渲染购物车数据
	function renderItems() {
		$strTop.find(".choose-item").remove();
		//每次操作之后先全部清空购物车
		var list = window.food_spu_tags || [];
		var totalPrice = 0;

		var tpl = '<div class="choose-item">' + '<div class="item-name">$name</div>' + '<div class="price">￥<span class="total">$price</span></div>' + '<div class="select-content">' + '<div class="minus"></div>' + '<div class="count">$chooseCount</div>' + '<div class="plus"></div>' + '</div>' + '</div>';

		list.forEach(function(item) {
			item.spus.forEach(function(_item) {
				if (_item.chooseCount > 0) {
					var price = _item.chooseCount * _item.minPrice;
					var row = tpl.replace("$name", _item.name).replace("$price", price)//总价
					.replace("$chooseCount", _item.chooseCount);
					totalPrice += price;

					//挂载数据
					var $row = $(row);
					$row.data("itemData", _item);
					$strTop.append($row);
				}
			})
			window.shopBar.changeAllPrice(totalPrice);
			//更新总价
			changeDot();
			//更新红点
		})
	}


	window.shopBar.renderItems = renderItems;

	// //更新单商品总价
	function changeShippingPrice(str) {
		$strBottom.find(".shipping-fee").text(str);
	}


	window.shopBar.changeShippingPrice = changeShippingPrice;
	// //更新全商品总价
	function changeAllPrice(str) {
		$strBottom.find(".total-price-span").text(str);
	}


	window.shopBar.changeAllPrice = changeAllPrice;

	//购物车里的加号事件
	$strTop.on("click", ".plus", function(e) {
		var $count = $(e.currentTarget).parent("").find(".count");
		$count.text(parseInt($count.text() || "0") + 1);

		//更新挂载的数据
		var $item = $(e.currentTarget).parents(".choose-item").first();
		var itemData = $item.data("itemData");
		itemData.chooseCount++;
		//更新挂载数据，在列表里也会同步更新

		window.shopBar.renderItems();
		//购物车渲染

		//找到当前右侧详情的数据，进行联动
		$(".left-item.active").click();
	})
	//购物车里的减号事件
	$strTop.on("click", ".minus", function(e) {
		var $count = $(e.currentTarget).parent("").find(".count");
		if ($count.text() == 0)
			return;

		$count.text(parseInt($count.text() || "0") - 1);

		//更新挂载的数据
		var $item = $(e.currentTarget).parents(".choose-item").first();
		var itemData = $item.data("itemData");
		itemData.chooseCount--;
		//更新挂载数据，在列表里也会同步更新

		window.shopBar.renderItems();
		//购物车渲染

		//找到当前右侧详情的数据，进行联动
		$(".left-item.active").click();
	})
	//点击显示或者隐藏购物车
	$(".shop-icon").on("click", function() {
		$(".mask").toggle();
		$strTop.toggle();
	})
	//清空购物车
	$(".clear-car").on("click", function(e) {
		if ($strTop.find(".choose-item").length != 0) {
			var r = confirm("确认清空购物车吗？");
			if(r){
				$strTop.find(".choose-item").remove();
				$(".dot-num").hide().text(0);
				$strBottom.find(".total-price-span").text(0);
				//将挂载的数据清零
				var list = window.food_spu_tags || [];
				list.forEach(function(item) {
					item.spus.forEach(function(_item) {
						if (_item.chooseCount > 0) {
							_item.chooseCount = 0;
						}
					})
				})
				// 清空购物列表的数字
				$(".menu-item").find(".count").text("0");
				$(".mask").toggle();
				$strTop.toggle();
			}
		}
	})
	//	结算按钮
	$(".submit-btn").on("click", function(e) {
		var ary = [];
		var carinfo = window.food_spu_tags || [];
		carinfo.forEach(function(item) {//获得每一种产品分类下的所有产品
			item.spus.forEach(function(items) {//获得每一种产品的明细
				if (items.chooseCount != 0) {
					ary.push(items);
				}
			})
		})
		if (ary.length != 0) {
			var data = {
				"orderinfo" : ary
			}
			//在跳转订单界面的时候，将这个信息保存到订单中j
			$.ajax({
				url :"http://172.11.1.94:4396/order/order",
				type : "POST",
				contentType : 'application/json',
				data : JSON.stringify(data),
				success : function(data) {
					var orderid = data.msg;
					localStorage.setItem("orderid",orderid)
					window.location.href = "pay";
				}
			})

		} else {
			alert("请选择商品");
		}
	})

	// //计算红点中的数量
	function changeDot() {
		var counts = $strTop.find(".count");
		var total = 0;
		for (var i = 0; i < counts.length; i++) {
			total += parseInt($(counts[i]).text());
		}
		if (total > 0) {
			$(".dot-num").show().text(total);
		} else {
			$(".dot-num").hide();
		}
	}

}


