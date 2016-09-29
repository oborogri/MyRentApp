# MyRentApp
# Author: Grigore Oboroceanu
# Version: 20-08-2016
#
Project built in Eclipse using Play framework
#
# Login credentials:
# Administrator: username: admin@witpress.ie/ password: secret
# Landlord: homer@simpson.com/ password: secret
# Tenant: bart@simpson.com/ password: secret
#
# Application deployed to Heroku:  http://my-rent20073381.herokuapp.com
#
This app facilitates administrator login and register landlord and tenant. Then landlord can login, add new residences, edit or delete existing residences. He may generate report with registered residences for specified region on the map.
Signed up tenant can login and check his current residence eircode, may end up current residence and choose new residence from available vacant residences. He is presented with a map with available residences on it. 
The administrator has options to register tenant/landlord, delete existing tenant/landlord, generate reports and charts on registered residences. 
The deletion/registration of tenants/landlords is dynamically reflected on the residences map.

# Assignment tasks (part 1): 
Develop MyRentApp, the purpose of which is to allow a user to register, log in and log out, gather data on residential properties, store the data in a database and generate reports. .

This being a prototype, only a subset of the necessary data to define a property should be gathered:

includes the property's geolocation
the occupancy status: whether rented or vacant
the rent payable
the number of bedrooms
the residence type such as apartment, house and so on.
Develop the project in 4 iterations:

Each iteration to comprise a completed, working, error-free, part of the application as specified.
Add tags to each iteration.

# Iteration v0: 

Develop the sign up and sign in facility.
Introduce a model named User:

A user should be capable of signing on (registering) and logging in.
It should be possible to open the database and verify that the user is recorded in User.

# Iteration v1: 

Build on iteration v0 by adding:
a residence model,
an input data controller and
a view template to accept user data.

# Iteration v2: 

Build on iteration v1 by adding a report view that allows the user to select a circular area 
within which all properties are listed together with specified attributes

# Iteration v3: 
This is an optional iteration.

Complete the application building on iteration v2.
Provide a template to allow the user send feedback.


# Part 2 of the assignment comprises the extension of the MyRent web app developed during the first part by the addition of features described in a series of supplied stories.  Additionally, the use of Git to manage and track the project.

• Story 1:
Provide for additional Residence fields
• int numberBathrooms;
• int area; // the area of the residence in square metres
• Refactor the input data template (html) page appropriately.

Story 2:
Report to contain extra columns showing new Residence fields.
Include new fields in data.yml.

Story 4:
Add a landlord Edit Profile component with menu access to logged-in
landlord from input data page. Allow edit of name and address.

Story 3:
Rename model User to Landlord, rename controller Accounts to
Landlords and refactor all dependencies appropriately.

Story 5:
Add a Tenant model and associated controller (Tenants) and views.
Provide sign up and login features. Tenant fields to be firstName,
lastName, email, password and Residence reference. Implement
authorisation.

The welcome or home page should now contain three fully implemented
menu items: Home, Landlord, Tenant. The latter two should lead to login
pages for Landlord and Tenant respectively.
Refactor the data.yml file, ensuring it contains sample Residence, 

Story 6:
Add Landlord configuration page comprising Add Residence and Edit
Landlord Profile buttons, Delete Residence and Edit Residence dropdown
boxes.
The Add Residence button press switches to Input Data page (existing).
Edit Landlord Profile switches to Edit Profile page (existing).
Populate dropdowns with list residences. Actions implementations later.

Story 7:
Implement Delete Residence.
Implement Edit Residence. Allow editing of rent field only. Display Eircode
as readonly field.

Story 8:
New model Residence fields:
Landlord landlord: one to many. Landlord may own many residences.
Tenant tenant: one to one. One tenant per residence and vice versa.
Update yml file with additional sample data.

Story 9:
Residence field:
The residence field String rented is redundant as this state is captured
by value of the Tenant reference. Delete the redundant field.
Remove the corresponding radio button pair in the Input data page.
Fix any anomalies that may arise and modify dependencies
appropriately.

Story 10:
Logout:
Use a separate Session scope to maintain a record of each logged-in
user - Landlord or Tenant.
Implement a logout action for each of the users.
Add a menu entry with appropriate routing.

Story 11:
On successful authorisation a tenant should be routed to a page with these
features:
Readonly eircode representing existing rental.
Button to terminate tenancy.
Dropdown to allow selection of new tenancy from list vacant residences.
Googlemap-based vacant residences search module.
Fully implement the report (search result) + model, view & controller code.

Story 12:
Implement an Administration module comprising:
Administrator model containing hardcoded email & password
Use: admin@witpress.ie, secret
Login in page with corresponding Administrators controller
authorisation. Add a logout feature also to all Admin pages.
On login, switch to administration page containing:
Button to register (signup) landlord.
Button to register (signup) tenant.
Google map representing all residences with markers + tooltips
stating eircode & tenant. 

Story 14:
Implement Administrator reporting module. The html (template) page
should include filters to generate reports by rented status (vacant or
rented), by type (house, flat and so on), by amount rent sorted ascending
or descending and the unfiltered list.
The columns should include eircode, date residence registered, type,
number bedrooms, number bathrooms, rent (€), area, landlord name,
rented status and tenant if any.

Story 13:
Administration page - add:
Button to delete tenant.
Button to delete landlord.
Refactor map, model, view & controller code as appropriate to reflect any
Landlord or Tenant deletions.

Story 15:
Input data page - add:
Facility to manually obtain eircode correspoding to GPS (lat, long)
using EirCODE Finder.

Story 16:
Implement a charting module accessible to the logged in administrator
and comprising:
Table of residences data: landlord, eircode & rent.
Pie chart representing landlords total rent roll as a percentage of the
entire database residence portfolio income.

Story 17:
Implement Semantic UI validation on all input controls. Use external
JavaScript files and locate them in public/javascripts folder. Note that the
validation approach has changed from that used in Semantic version 1.
Apply airbnb styling to JS files using WebStorm.

Story 18 (Ajax):
Administration page
Implement jQuery ajax for:
Delete Landlord
Delete Tenant
Changes to map resulting from either above deletion.
Dynamically adjust dropdowns’ content to reflect deleted tenant or
landlord.
