package com.hms.billing;
import java.util.*; import org.springframework.data.jpa.repository.JpaRepository;
public interface SubscriptionInvoiceRepository extends JpaRepository<SubscriptionInvoice,UUID>{List<SubscriptionInvoice> findAllByTenantIdOrderByIssuedOnDesc(UUID tenantId);Optional<SubscriptionInvoice> findByIdAndTenantId(UUID id,UUID tenantId);}
