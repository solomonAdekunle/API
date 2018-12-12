# new feature
# Tags: optional
    
Feature: A description

#  @Test
#Scenario Outline: Verify the status codes is 200 ok
#    Given I perform a GET operation for ergast api with statusCode as 200
#     Then I should see the total size return as 20
#     Then  I should see some of <countries>
#   Examples:
#  |countries            |
#  |Italian,British,British|



  Scenario Outline: Setting a query parameters and verifying the status code
    Given I access ergast api
    When  I set query parameters for limit as 3
    Then I should see query parameters for limit as 4 and get status code 200
    Then  I should see some of <countries>
  Examples:
       |countries              |
       |Italian,British,British|


