package com.edi.im.route.kit;

import com.alibaba.fastjson.JSON;
import com.edi.im.route.cache.ServerCache;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Function: Zookeeper 工具
 *
 * @author crossoverJie
 * Date: 2018/8/19 00:33
 * @since JDK 1.8
 */
@Component
public class ZKit {

    private static Logger LOGGER = LoggerFactory.getLogger(ZKit.class);


    @Autowired
    private ZkClient zkClient;

    @Autowired
    private ServerCache serverCache;


    /**
     * 监听事件
     *
     * @param path
     */
    public void subscribeEvent(String path) {
        zkClient.subscribeChildChanges(path, (parentPath, currentChilds) -> {
            LOGGER.info("清除/更新本地缓存 parentPath=【{}】,currentChilds=【{}】", parentPath, currentChilds.toString());
            //更新所有缓存/先删除 再新增
            serverCache.updateCache(currentChilds);
        });


    }

    /**
     * 获取所有注册节点
     *
     * @return
     */
    public List<String> getAllNode() {
        List<String> children = zkClient.getChildren("/route");
        LOGGER.info("查询所有节点成功=【{}】", JSON.toJSONString(children));
        return children;
    }


}
