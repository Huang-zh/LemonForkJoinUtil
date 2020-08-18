## 个人封装的Fork-Join小工具

封装了JDK内置的Fork-join框架，用多线程处理一个个小单元（每个单元由单独的线程处理，所有单元的执行逻辑均相同）。

适用场景：纵表转树形结构

使用规则：
* 开发者自定义任务调度类，需要实现Mytask接口并实现内部的execute方法
* 调用ForkJoinUtil的getResult方法，将纵表原集合和定制的Mytask的实现类一并传入，获取结果即可。
``` java
XxxxTask task = new XxxxTask(list);
List<Object> resultList =  ForkJoinUtil.getResult(list, achievementFileTask,0,list.size(),5);
```

<strong>getResult方法的参数语义如下：</strong>
* destination，纵表数据集合
* myTask，定制分治任务
* start，集合的最小索引，一般为0
* end ，集合的最后一个索引+1，可理解为集合的size
* threshHold，分治因子，即每个线程处理多少条数据，end-start>threshHold的情况下将会内部自动递归分解任务，一般无需开发者关心。


注：如果想要在拿到树形结构后再做处理，请自行强制转化。

huang.zh ^_^
