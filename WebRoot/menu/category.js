function Category(objName){
	this.obj=objName;
	this.categoryList=[];
}
Category.prototype.getCategory=function(categoryName,url,frameName){
	var len=this.categoryList.length;
	this.categoryList[len]=new categoryItem(categoryName,url,frameName);
}
function categoryItem(itemName,url,frameName) {
	this.itemName=itemName;
	this.url=url;
	this.frameName=frameName;
}
Category.prototype.toString=function(){
	var str="";
	for(var i in this.categoryList){
		// str += '<li class=\"layui-nav-item\" onclick=\"(\''+this.categoryList[i].url+'\',\''+this.categoryList[i].frameName+'\')">'+'<a href="">'+this.categoryList[i].itemName+'</a></li>'
		str+= '<li class="layui-nav-item"><a href="'+this.categoryList[i].url+'">'+this.categoryList[i].itemName+'</a></li>';
	}
	return str;
}

