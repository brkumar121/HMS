package com.hms.branches;
import jakarta.validation.constraints.*;
public record CreateBranchRequest(@NotBlank @Size(max=40) String code,@NotBlank @Size(max=180) String name,@Size(max=500) String address,@Size(max=40) String phone){}
