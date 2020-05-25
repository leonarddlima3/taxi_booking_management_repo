Füber Taxi Service
------------------
------------------


Modules consists
----------------
1. On-board Taxi on the platform
    This module helps in on-boarding the taxi on the platform by submitting the details such as Vehicle Number, Model, Driver associated.
2. Update taxi location details
    This module updates the location of the taxi. The caller of this module is associated with the tracking system for updating location.
3. Registers Customer on the platform
    This module helps in registering customer on the platform by submitting customer details along with phone number.
4. Update customer location details
    This module updates the location of customer holding the device. The caller of this module is associated with the tracking system involving the customer's device for updating location.
5. Taxi called by the Customer based on the location and taxi preferences
    This module provides a mechanism to book a taxi and locks the taxi such that other customer nearby wont be able to view the same.
6. Ride started by the driver
    This module initiates the ride and logs ride start and source location data to be used in fare calculation.
7. Ride ended by the driver
    This module triggers to end the ride and logs ride end and destination location data and computes fare based on the computing logic.


Technical Specifications
------------------------
1. Platform framework : (Java v8, Spring boot v2.3.0)
    Used for the business logic and to serve the requests to the Back-end.
2. Database : (SQLite v3.31.1)
    Used for in memory database to store Taxi, Customer, Ride details.
3. Memory: (Hashing)
    Used for storing the location data for the Taxi in terms of latitude and longitude for faster search across the platform.
