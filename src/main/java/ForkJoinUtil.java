
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.Collectors;

/**
 * @Auther: huang.zh
 * @Date: 2020/3/3 13:42
 * @Description:
 */
public class ForkJoinUtil {

    public static List<Object> getResult(List<?> destination,MyTask myTask
            ,Integer start,Integer end,Integer threshHold) throws ExecutionException, InterruptedException {
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<List<Object>> task = pool.submit(new ListToTreeTask(destination,myTask,start,end,threshHold));
        return task.get().stream().distinct().collect(Collectors.toList());
    }
}
