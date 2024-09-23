package animal.application.order.application.order.util;

import animal.application.order.dto.OrderResponse.GetHubIdRes;
import animal.application.order.dto.OrderResponse.GetNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

//메모리 부족
public class HubUtil {

    private List<HubNode> hubList; // 각 허브 노드를 저장하는 리스트

    public Map<String, Object> estimatedData(GetHubIdRes dto) {
        var map = new HashMap<String, Object>();

        HubNode startNode = findNodeBySeq(dto.startHubId());
        HubNode endNode = findNodeBySeq(dto.endHubId());

        // 출발 시퀀스 > 도착 시퀀스: 오른쪽(rt) 방향으로 탐색
        if (startNode.getSequence() > endNode.getSequence()) {
            return dfs(startNode, endNode, true);
        } else {
            // 출발 시퀀스 < 도착 시퀀스: 왼쪽(lt) 방향으로 탐색
            return dfs(startNode, endNode, false);
        }
    }

    // DFS 탐색을 통해 추정 시간을 계산하는 메서드
    private Map<String, Object> dfs(HubNode startNode, HubNode endNode, boolean directionRt) {
        int totalTime = 0;
        int totalDis = 0;

        HubNode currentNode = startNode;

        while (currentNode != null && !currentNode.equals(endNode)) {
            totalTime += currentNode.getEstimatedTime();
            totalDis += currentNode.getEstimatedDistance();

            // 방향에 따라 다음 노드를 선택
            if (directionRt) {
                currentNode = currentNode.getRt(); // 오른쪽으로 이동
            } else {
                currentNode = currentNode.getLt(); // 왼쪽으로 이동
            }
        }

        if (currentNode != null) {
            totalTime += currentNode.getEstimatedTime();
            totalDis += currentNode.getEstimatedDistance();// 마지막 노드의 시간 더하기
        }

        Map<String, Object> result = new HashMap<>();
        result.put("totalTime", totalTime);
        result.put("totalDis", totalDis);

        return result;
    }

    // 특정 시퀀스를 가진 허브 노드를 찾는 메서드
    private HubNode findNodeBySeq(UUID hubId) {
        for (HubNode node : hubList) {
            if (node.getStartId().equals(hubId)) {
                return node;
            }
        }
        return null;
    }

    // 허브 리스트를 초기화하는 메서드
    public void init(List<GetNode> hubList) {
        this.hubList = new ArrayList<>(); // 허브 리스트 초기화
        HubNode pre = null;

        for (GetNode n : hubList) {
            Random random = new Random();
            Integer newEstimatedTime = random.nextInt(121); // 0~120 분 랜덤
            Integer newEstimatedDistance = random.nextInt(1000) + 1; // 1~1000 미터 랜덤
            HubNode cur = new HubNode(n.id(), n.sequence(), newEstimatedTime, newEstimatedDistance);

            if (pre != null) {
                pre.setRt(cur);
                cur.setLt(pre);
            }

            this.hubList.add(cur);
            pre = cur;
        }
    }
}
