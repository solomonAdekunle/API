# new feature
# Tags: optional
    
Feature: A description

 Scenario: Check that indexable-filters service returns correct response
    Given I perform pathParams operation with authentication for an /indexable-filter/storeId/{storeId}/categoryId/{categoryId}/language/{language}/v1
    |categoryId|storeId|language|
    |PSF_438811|GB_1   |en      |
   Then I verify response StatusCode is correct as HTTP/1.1 200 OK
    And I verify response time is less than 2000L
    And I verify applied dimension values are as expected
    And I verify response schema is as expected
    And I verify cache-control has max-age=86400


  Scenario: Check that indexable-filters service returns correct response without authentification
    Given I perform pathParams operation without authentication for an /indexable-filter/storeId/{storeId}/categoryId/{categoryId}/language/{language}/v1
     |categoryId|storeId|language|
     |PSF_438811|GB_1   |en      |
    Then I verify response StatusCode is correct as HTTP/1.1 401 Unauthorized

  Scenario: Check that Seo text returns empty when all seo text contain a javascript content type
    Given I perform pathParams operation with authentication for an /indexable-filter/storeId/{storeId}/categoryId/{categoryId}/language/{language}/v1
      |categoryId|storeId|language|
      |PSF_437408|GB_1   |en      |

    Then I verify response StatusCode is correct as HTTP/1.1 200 OK
    And I verify the groups.seoText field is empty


  Scenario: Check category with multiple dimensions are returned correctly
    Given I perform pathParams operation with authentication for an /indexable-filter/storeId/{storeId}/categoryId/{categoryId}/language/{language}/v1
      |categoryId|storeId|language|
      |PSF_432164|GB_1   |en      |
    Then I verify response StatusCode is correct as HTTP/1.1 200 OK
    Then I should see service returning more than one applied dimension within the xml file

