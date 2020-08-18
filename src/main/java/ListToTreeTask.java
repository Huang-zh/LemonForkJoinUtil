
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * @Auther: huang.zh
 * @Date: 2020/3/3 13:35
 * @Description:
 */
public class ListToTreeTask extends RecursiveTask<List<Object>> {

    private List<?> destination;

    private MyTask realTask;

    private Integer start;

    private Integer end;

    private final Integer THRESH_HOLD;

    public ListToTreeTask(List<?> destination, MyTask realTask, Integer start, Integer end, Integer THRESH_HOLD) {
        this.destination = destination;
        this.realTask = realTask;
        this.start = start;
        this.end = end;
        this.THRESH_HOLD = THRESH_HOLD;
    }

    @Override
    protected List<Object> compute() {
        assert start < end;
        if (destination.size() < THRESH_HOLD){
            return (List<Object>)realTask.execute();//未超过阈值，线程内递归
        }else{
            try {
                //超过阈值，分治
                //将原数据集合二分，启动两个线程处理
                int mid = (start + end)/2;
                ListToTreeTask task1 = new ListToTreeTask(destination.subList(start,mid)
                        ,realTask,start,mid,THRESH_HOLD);
                task1.fork();
                ListToTreeTask task2 = new ListToTreeTask(destination.subList(mid+1,end)
                        ,realTask,mid+1,end,THRESH_HOLD);
                task2.fork();
                List<Object> result4Task1 = task1.join();
                result4Task1.addAll(task2.join());//合并结果
                return result4Task1;
            }catch (Exception e){
                //ignore
            }
            return new ArrayList<>();
        }
    }
}
