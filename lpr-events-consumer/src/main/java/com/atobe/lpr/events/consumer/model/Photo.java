/*
 * License Plate Review (LPR) API
 * License Plate Review (LPR) API is a module of a tolling BackOffice system, responsible for allowing authorized users reviewing photos of vehicles which were not successfuly recognized in terms of the license plate number. In order to simplify a development only a small set of operations is described below. The system receives photos in asyncronous way by means of a message broker.
 *
 * OpenAPI spec version: 1.0.0
 * Contact: you@your-company.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package com.atobe.lpr.events.consumer.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Photo implements Serializable {

  private Long photoId;

  private String fileName;

  private String filePath;
}
