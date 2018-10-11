package us.codecraft.webmagic.processor.example;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class GithubRepoPageProcessorExam implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    @Override
    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links().regex("(https://webmagic\\.io/\\w+/\\w+)").all());
        page.putField("author", page.getUrl().regex("https://webmagic\\.io/(\\w+)/.*").toString());
        page.putField("name", page.getHtml().xpath("//*[@id=\"41-实现pageprocessor\"]").toString());
        if (page.getResultItems().get("name") == null) {
            //skip this page
            page.setSkip(true);
        }
        page.putField("firstMenu", page.getHtml().xpath("/html/body/div/div[1]/nav/ul/li/a/text()").all());
        page.putField("secondMenu", page.getHtml().xpath("/html/body/div/div[1]/nav/ul/li/ul/li/a/text()").all());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new GithubRepoPageProcessorExam())
                .addUrl("http://webmagic.io/docs/zh/posts/ch4-basic-page-processor/pageprocessor.html")
//                .addPipeline(new JsonFilePipeline("D:\\magic\\"))
                .thread(5).run();
    }
}
