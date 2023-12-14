package com.car.dto

import lombok.Builder
import lombok.Getter
import lombok.Setter

@Getter
@Setter
@Builder
data class ResponseData<D>(
    var items: List<D>? = ArrayList<D>(),
    var totalCount: Int? = 0
)