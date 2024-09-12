package animal.domain;

import animal.domain.manager.CompanyDeliveryManager;
import animal.domain.manager.HubDeliveryManager;
import animal.domain.manager.HubManager;
import animal.domain.manager.ProviderCompanyManager;
import animal.jpa.BaseEntity;
import exception.GlobalException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import response.ErrorCase;

@Getter
@Entity
@Table(name = "p_hub")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hub extends BaseEntity {

    private static final int MAX_COMPANY_DELIVERY_MANAGER_COUNT = 10;

    @EmbeddedId
    private final HubId id = HubId.ofRandom();

    @Embedded
    private Address address;

    @Embedded
    private Coordinate coordinate;

    @OneToMany(mappedBy = "hub", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Inventory> inventoryList = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "p_hub_managaer", joinColumns = @JoinColumn(name = "hub_manager_id"))
    private List<HubManager> hubManagerList = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "p_hub_delivery_managaer", joinColumns = @JoinColumn(name = "hub_delivery_manager_id"))
    private List<HubDeliveryManager> hubDeliveryManagerList = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "p_company_delivery_managaer", joinColumns = @JoinColumn(name = "company_delivery_manager_id"))
    private List<CompanyDeliveryManager> companyDeliveryManagerList = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "p_provider_company_manager", joinColumns = @JoinColumn(name = "provider_company_manager_id"))
    private List<ProviderCompanyManager> providerCompanyManagerList = new ArrayList<>();

    @Builder
    private Hub(Address address, Coordinate coordinate) {
        this.address = address;
        this.coordinate = coordinate;
    }

    /**
     * 허브 정보 수정
     */
    public void updateHubInfo(Address address, Coordinate coordinate) {
        this.address = address;
        this.coordinate = coordinate;
    }

    /**
     * 재고 추가
     */
    public void addInventory(Inventory inventory) {
        inventoryList.add(inventory);
    }

    /**
     * 재고 단건 조회
     */
    public Optional<Inventory> getInventory(InventoryId inventoryId) {

        return inventoryList.stream()
            .filter(inventory -> inventory.getId().equals(inventoryId))
            .findAny();
    }

    /**
     * 재고 삭제
     */
    public void removeInventory(InventoryId inventoryId) {
        inventoryList.removeIf(inventory -> inventory.getId().equals(inventoryId));
    }

    /**
     * 허브 매니저 추가
     */
    public void addHubManager(HubManager hubManager) {
        hubManagerList.add(hubManager);
    }

    /**
     * 허브 배송 매니저 추가
     */
    public void addHubDeliveryManager(HubDeliveryManager hubDeliveryManager) {
        hubDeliveryManagerList.add(hubDeliveryManager);
    }

    /**
     * 업체 배송 매니저 추가
     */
    public void addCompanyDeliveryManager(CompanyDeliveryManager companyDeliveryManager) {
        if (isCompanyDeliveryManagerOverLimit()) {
            throw new GlobalException(ErrorCase.COMPANY_DELIVERY_MANAGER_OVER_LIMIT);
        }

        companyDeliveryManagerList.add(companyDeliveryManager);
    }

    private boolean isCompanyDeliveryManagerOverLimit() {
        return companyDeliveryManagerList.size() >= MAX_COMPANY_DELIVERY_MANAGER_COUNT;
    }

    /**
     * 생산 업체 매니저 추가
     */
    public void addProviderCompanyManager(ProviderCompanyManager providerCompanyManager) {
        providerCompanyManagerList.add(providerCompanyManager);
    }
}
