package animal.application.order.application.order.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HubUtil {

    Integer estTime = 0;
    Integer estDis = 0;
    boolean dir = true;

    public void dfs(HubNode curr, HubNode end) {
        if (curr.getStartId() == end.getStartId()) {
            return;
        }
        estTime += curr.getEstimatedTime();
        estDis += curr.getEstimatedTime();
        if (dir) {
            dfs(curr.getRt(), end);
        }
        dfs(curr.getLt(), end);
    }


    //허브 예상 거리와 시간
    public Map<String, Object> estimatedTimeData(Map<String, Object> req) {
        var map = new HashMap<String, Object>();

        //출발 sequence> 도착 sequence rt로 돌기
        int startSeq = Integer.parseInt((String) req.get("startSeq"));
        int endSeq = Integer.parseInt((String) req.get("endSeq"));
        HubNode startNode;
        HubNode endNode;
        //hublist에서 값 받아오기
       /* dfs(startNode, endNode);

        //출발 sequence< 도착 sequence lt로 돌기
        dir = false;
        dfs(startNode, endNode);*/
        return map;

    }


    //초기화
    //각 허브센터 id를 받아와야함.
    public void init(UUID startId, Integer sequence) {

    }

}
