package us.codecraft.webmagic.pipeline;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.digest.DigestUtils;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.utils.FilePersistentBase;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class JsonFilePipeline extends FilePersistentBase implements Pipeline {

    public JsonFilePipeline() {
        setPath("/data/webmagic/");
    }

    public JsonFilePipeline(String path) {
        setPath(path);
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        try {
            String path = this.path + PATH_SEPERATOR + task.getUUID() + PATH_SEPERATOR;
            String mapStr = JSON.toJSONString(resultItems.getAll());
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(getFile(path + DigestUtils.md5Hex(resultItems.getRequest().getUrl()) + ".txt")),"UTF-8"));
            printWriter.println(mapStr);
            printWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
