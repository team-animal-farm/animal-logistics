package animal.application.order.dto;

import java.util.List;
import java.util.UUID;

public class OrderResponse {

  public record CreateOrderReq(
      UUID receiveCompanyId,
      UUID providerCompanyId,
      List<OrderProduct> products,
      String comment
  ) {

  }

  public record OrderProduct(
      UUID productId,
      Integer quantity
  ) {

  }

  public record GetProductRes(
      UUID productId,
      Integer quantity,
      Integer price
  ) {

  }

  public record GetHubIdReq(
      UUID receiveCompanyId,
      UUID providerCompanyId
  ) {

  }

  public record GetHubIdRes(
      UUID endHubId,
      UUID startHubId
  ) {

  }


}
