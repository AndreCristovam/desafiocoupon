package com.andrecristovam.desafiocoupon.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andrecristovam.desafiocoupon.api.controller.dto.CouponResponse;
import com.andrecristovam.desafiocoupon.api.controller.dto.CreateCouponRequest;
import com.andrecristovam.desafiocoupon.api.controller.mapper.CouponApiMapper;
import com.andrecristovam.desafiocoupon.api.controller.mapper.CouponCommandMapper;
import com.andrecristovam.desafiocoupon.application.usecase.CreateCouponUseCase;
import com.andrecristovam.desafiocoupon.application.usecase.DeleteCouponUseCase;
import com.andrecristovam.desafiocoupon.application.usecase.GetCouponUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponController {

	private final CreateCouponUseCase createCouponUseCase;
	
	private final DeleteCouponUseCase deleteCouponUseCase;
	
	private final GetCouponUseCase getCouponUseCase;
	
	private final CouponCommandMapper commandMapper;
	
    private final CouponApiMapper apiMapper;
	
	@GetMapping("/{id}")
	public ResponseEntity<CouponResponse> getById(@PathVariable String id) {

	    var coupon = getCouponUseCase.executeById(id);
	    
	    return ResponseEntity.ok(apiMapper.toResponse(id, coupon));
	}

	@PostMapping
	public ResponseEntity<CouponResponse> create(@RequestBody CreateCouponRequest request) {

		var result = createCouponUseCase.execute(commandMapper.toCommand(request));

		return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(apiMapper.toResponse(result.id(), result.coupon()));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {

	    deleteCouponUseCase.execute(id);

	    return ResponseEntity.noContent().build();
	}
}
