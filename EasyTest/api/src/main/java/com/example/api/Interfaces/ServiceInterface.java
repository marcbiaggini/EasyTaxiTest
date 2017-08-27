package com.example.api.Interfaces;

import com.example.api.Models.Places;

import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.api.RestClientErrorHandling;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import lombok.experimental.NonFinal;

/**
 * Created by juan.villa on 26/08/17.
 */
@NonFinal
@Rest(rootUrl = "http://private-830fa5-esdrasdl.apiary-mock.com/favorites", converters = {MappingJackson2HttpMessageConverter.class})
public interface ServiceInterface extends RestClientErrorHandling {

  @Get("")
  Places getFavoritesPlaces();

}
