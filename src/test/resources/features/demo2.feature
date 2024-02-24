#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
#@tag
Feature: Verify demo2
  I want to verify demo2
#
#@paymentGateway
#@test2
#Scenario: Test the demo2
#Given I launch payment gateway
#Then I generate card details
#When I buy the toys by selecting quantity


@orange1
Scenario: Orange Test
Given I login to Orange HRM app
Then Dashboard should be launched by default
When I navigate to My Info section
Then Date of birth details should be displayed
When I Update Date of birth details
Then My Info page should be updated with latest selected date of birth
  When I logout from the app
  Then Login page should be displayed

@orange
Scenario: Orange Test failure demo
Given I login to Orange HRM app with invalid details
Then Dashboard should be launched by default

