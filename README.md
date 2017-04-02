# AndroidDemo<br/>
#AndroidNewWidgetsDemo<br/>
按照http://www.jianshu.com/p/533f5ecee306<br/>

一些笔记<br/>
http://www.jianshu.com/p/533f5ecee306 <br/>
AndroidNewWidgetsDemo实现过程详解<br/>

下面使用的控件在design库中，要添加的依赖 ‘com.android.support.design:’<br/>
1.activity_main布局<br/>
由DrawerLayout组成<br/>
DrawerLayout是Support Library包中实现侧滑菜单效果的控件，一般放在根目录，是一个布局控件，只是自带了滑动的功能，按照规定的方式写布局，就能实现侧滑效果<br/>
关于DrawerLayout，一般由两个子布局组成，一个是当前显示的布局，一个是侧滑显示的布局<br/>
drawerLayout左侧菜单（或者右侧）的展开与隐藏可以被DrawerLayout.DrawerListener的实现监听到<br/>
DrawerLayout的布局是由两个子布局组成的，一个是CoordinatorLayout，一个是NavigationView<br/>
CoordinatorLayout在content_main布局中，使用下面语句加载到activity_main中<br/>
         <include layout="@layout/content_main"/><br/>
关于NavigationView，被定义为抽屉，主要是结合DrawerLayout使用；<br/>
Navigation是一个导航菜单框架， NavigationView的两个自定义属性<br/>
app:headerLayout接收一个layout，作为导航菜单顶部的Header，可选项。<br/>
app:menu接收一个menu，作为导航菜单的菜单项，几乎是必选项，不然这个控件就失去意义了。但也可以在运行时动态改变menu属性。<br/>
关于NavigationView的使用见下面两篇文章<br/>
http://www.jianshu.com/p/76e30f87a4ed<br/>
http://jaeger.itscoder.com/android/2016/02/16/use-navigation-view-detail.html <br/>
<br/>
2.content_main布局<br/>
根布局为CoordinatorLayout<br/>

AppBarLayout必须是CoordinatorLayout的直接子view，CoordinatorLayout中的滚动事件由APPBarLayout和滚动视图（如RecyclerView）联系，在滚动视图中添加behavior行为<br/>
app:layout_behavior="@string/appbar_scrolling_view_behavior"<br/>

当CoordinatorLayout发现RecyclerView中定义了这个属性，它会搜索自己所包含的其他view，看看是否有view与这个behavior相关联。<br/>AppBarLayout.ScrollingViewBehavior描述了RecyclerView与AppBarLayout之间的依赖关系。RecyclerView的任意滚动事件都将触发AppBarLayout或者AppBarLayout里面view的改变。<br/>
AppBarLayout里面定义的view只要设置了app:layout_scrollFlags属性，就可以在RecyclerView滚动事件发生的时候被触发<br/>
app:layout_scrollFlags属性里面必须至少启用scroll这个flag，这样这个view才会滚动出屏幕，否则它将一直固定在顶部。可以使用的其他flag有：<br/>


	* enterAlways: 一旦向上滚动这个view就可见。<br/>

	* enterAlwaysCollapsed: 顾名思义，这个flag定义的是何时进入（已经消失之后何时再次显示）。假设你定义了一个最小高度（minHeight）同时enterAlways也定义了，那么view将在到达这个最小高度的时候开始显示，并且从这个时候开始慢慢展开，当滚动到顶部的时候展开完。<br/>

	* exitUntilCollapsed: 同样顾名思义，这个flag时定义何时退出，当你定义了一个minHeight，这个view将在滚动到达这个最小高度的时候消失。<br/>


记住，要把带有scroll flag的view放在前面，这样收回的view才能让正常退出，而固定的view继续留在顶部。<br/>

关于对Toolbar的使用，Activity继承AppCompatActivity（继承自FragmentActivity），使用setSupportActionBar()时，要在style.xml中使用Theme.AppCompat.Linght.NoActionBar主题<br/>
可以修改为<br/>
<style name="AppTheme.Base" parent="Theme.AppCompat.Light.NoActionBar">
        <item name="android:windowNoTitle">true</item>
        <item name="android:windowActionBar">false</item>
</style>
不然运行时会报错，闪退<br/>
详见http://www.cnblogs.com/nangch/p/5347880.html <br/>
<br/>
关于TabLayout<br/>
通过选项卡的方式切换view并不是material design中才有的新概念。它们和顶层导航模式或者组织app中不同分组内容（比如，不同风格的音乐）是同一个概念。
TabLayout是和ViewPager结合使用的，ViewPager中的pageTitle对应的是TabLayout的显示<br/>
Design library的TabLayout 既实现了固定的选项卡 - view的宽度平均分配，也实现了可滚动的选项卡 - view宽度不固定同时可以横向滚动。选项卡可以在程序中动态添加：<br/>
TabLayout tabLayout = ...;<br/>
tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));<br/>
<br/>

但是如果你使用ViewPager在tab之间横向切换，你可以直接从 PagerAdapter 的 getPageTitle() 中创建选项卡，然后使用setupWithViewPager()将两者联系在一起。它可以使tab的选中事件能更新ViewPager,同时ViewPager 
的页面改变能更新tab的选中状态。<br/>
参考http://www.jcodecraeer.com/a/anzhuokaifa/developer/2015/0531/2958.html<br/>
http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0717/3196.html <br/>

<br/>
再来看看Fragment<br/>
frag_main布局<br/>
其中SwipeRefreshLayout为根节点<br/>
SwipeRefreshLayout 是一个下拉刷新控件，几乎可以包裹一个任何可以滚动的内容<br/>
SwipeRefreshLayout控件值允许有一个子元素：我们想滑动刷新的对象。它使用Listener机制来告之持有SwipeRefreshLayout的组建某个事件发生了，也就是说假如是activity持有SwipeRefreshLayout，那么activity就必须实现一个接口来接收通知，这个接口中需要实现的主要是onRefresh()方法。<br/>
除此之外SwipeRefreshLayout还提供了一些公共方法：<br/>
   setOnRefreshListener(OnRefreshListener): 为布局添加一个Listener<br/>
   setRefreshing(boolean): 显示或隐藏刷新进度条<br/>
   isRefreshing(): 检查是否处于刷新状态<br/>
setColorScheme(): 设置进度条的颜色主题，最多能设置四种<br/>
其中包括的是RecyclerView<br/>
关于RecyclerView<br/>
RecyclerView是谷歌V7包下新增的控件,用来替代ListView的使用,在RecyclerView标准化了ViewHolder类似于ListView中convertView用来做视图缓.<br/>
先来说说RecyclerView的有点就是,他可以通过设置LayoutManager来快速实现listview、gridview、瀑布流的效果，而且还可以设置横向和纵向显示，添加动画效果也非常简单(自带了ItemAnimation，可以设置加载和移除时的动画，方便做出各种动态浏览的效果),也是官方推荐使用的<br/>
<br/>
布局管理器将确定 RecyclerView 内各项目视图的位置并决定何时重新使用用户已不可见的项目视图。如果要重新使用（或重复使用）一个视图，布局管理器可能会要求适配器以数据集中的另一个元素替换视图的内容。 以此方式重复使用视图将可避免创建不必要的视图或执行成本高昂的 findViewById() 查找，从而改善性能。<br/>
RecyclerView 提供这些内置布局管理器：<br/>

	* LinearLayoutManager 以垂直或水平滚动列表方式显示项目。<br/>
	* GridLayoutManager 在网格中显示项目。<br/>
	* StaggeredGridLayoutManager 在分散对齐网格中显示项目。<br/>

如果要创建一个自定义布局管理器，请扩展 RecyclerView.LayoutManager 类别。<br/>
参见：http://frank-zhu.github.io/android/2015/01/16/android-recyclerview-part-1/ <br/>
官网https://developer.android.com/training/material/lists-cards.html#RecyclerView <br/>
<br/>

SwipeRefreshLayout中实现了刷新的效果<br/>
其中使用new handler是为了让刷新的设置在onMeasure()后<br/>

<br/>
前段时间在网上又看到小伙伴提出SwipeRefreshLayout的指示器不能显示的问题，该问题的出现情景是在Activity或者Fragmen的onCreate方法中直接调用SwipeRefreshLayout.setRefreshing(true)。<br/>
其实这个问题我在14年底的时候就遇到过了，后来在android issues上找到解决方案。本来不想写，后来想想时间过去两年了，Google还没对这个bug进行fix，那倒不如根据那个android issues做一个整理帮助更多未找到原因和解决方案的小伙伴们。<br/>
原因在SwipeRefreshLayout.onMeasure()之前，调用SwipeRefreshLayout.setRefreshing(true)。<br/>
解决方案<br/>
	* 很简单，那就是在SwipeRefreshLayout.onMeasure()之后，调用SwipeRefreshLayout.setRefreshing(true)。<br/>
	* 我把解决方案按照调用方式简单的分为两类：主调和回调。<br/>

主调Handler延迟时间执行，这个方案不好，因为延迟时间很难有一个准确数据，确保在任何机器上都是可行和完美的。<br/>
handler.postDelayed(new Runnable() {
    @Override
    public void run() {
        initiateRefresh();
    }
}, 1000); // 时间可以按自己需要设，不一定是1秒<br/>
感谢无奈的冻鱼提供的意见，使用Handler.post(runnable)。 那么为什么Handler.post(runnable)比较好呢？<br/>
原因非常简单，因为activity的onCreate()生命周期方法在调用setContentVIew()后，UI消息队列会包含绘制view的消息（view的绘制流程——measure，layout，draw），而UI消息队列又是按顺序执行的，所以这时候handler.post发出的消息会后执行，因此就相当于在view绘制完成后执行<br/>
<br/>

listview和RecyclerView的区别<br/>
http://dev.qq.com/topic/5811d3e3ab10c62013697408<br/>

Menu方面的<br/>
详见：http://blog.csdn.net/ahuier/article/details/9972387 <br/>
http://developer.android.com/guide/topics/ui/menus.html <br/>

<br>
<br>
<br>
#Mushroom<br>
基于上面的Demo<br>

这是一款针对食用菌的APP软件，全部数据来源于对此网站的爬虫http://www.emushroom.net/ （仅作为测试Demo，并非用于商业行为）<br>

关于Mushroom的框架，采用的是http://www.jianshu.com/p/533f5ecee306 ，即Material Design Library，详见官网<br>
最好是看完上面网站的Demo后（关于此详细讲解，见https://github.com/moluchase/AndroidDemo ），在来学习此Demo<br>


需要改进的地方<br>
1.关于WIFI连接，但是无法上网的问题，导致判断失误，造成无法联网的情况下，去执行可以上网的相关操作<br>
相关资料http://blog.csdn.net/never_cxb/article/details/47658257<br>
并没有解决<br>
<br>
2.关于第一次导入数据的问题，速度相当慢，如何实现用户快速的体验感<br>
（1） 我只是简单的将很多条数据在下载时一次性全部载入了<br>
（2） 在执行刷新时，当能联网时，我做的是将当前刷新页面的数据全部从数据库中删除，然后再次获取数据，这样导致刷新缓慢，如何只添加更新的消息<br>
<br>
3.智能化推荐<br>
