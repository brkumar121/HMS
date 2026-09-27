package com.hms.reports;
import java.util.Map;
public record AppointmentSummaryDto(long total,Map<String,Long> byStatus) {}
