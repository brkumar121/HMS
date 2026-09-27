package com.hms.onboarding;
import java.util.List;
public record SetupHealthDto(int completed,int total,List<SetupHealthItem> checks) {}
