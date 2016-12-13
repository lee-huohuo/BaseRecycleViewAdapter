# BaseRecycleViewAdapter
对于ListView的接班人RecycleView，我们在平常使用的时候还是发现了一些不便，但不得不承认，比ListView更灵活，一切自己动手～～～
        改封装类涵盖了ListView的OnItemClickListener  
        
        
        mainActivity_Adapter.setOnItemClickListener(new FatherRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();

            }
        });
        
        
        setEmpty(View view)方法，带有上拉加载的功能，当FooterView显示在
        用户可见区域时，会回调OnGetData接口，我们在Activity Or Fragment类里，使用
        
        
        
        Adapter.setOnGetDataListener(new FatherRecycleViewAdapter.OnGetDataListener() {  
            @Override
            public void onGetData() {
            }
        });
在onGetData进行我们的更新请求，当数据添加后，如：在请求网络数据返回的JSON，解析成功并且添加入List，我们根据此时返回的JSON数组大小
来设置是否需要继续显示或隐藏FooterView  Adapter.setShowFooter(true)(设置为false时会显示"暂无数据"的布局)，最后调用notifyDat
aSetChanged()方法。
在第一次请求网络数据返回的JSON数据为空时，同上，Adapter.setShowEmpty(true)显示用来提示用户没有改数据的 提示布局，具体代码请查看Demo
