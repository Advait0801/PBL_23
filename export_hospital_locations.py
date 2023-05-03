import pandas as pd
import requests
import urllib
import pickle
import random

hospitals_df = pd.read_csv( "hospitals/hospitals_pune.csv" , usecols=[ "Ward Name" , "Ward No." ] )
hospitals_df.dropna( inplace=True )

locations = hospitals_df[ "Ward Name" ].values.reshape( ( -1 , ) ).tolist()
names = hospitals_df[ "Ward No." ].values.reshape( ( -1 , ) ).tolist()

LOCATION_SUFFIX = ", Pune, Maharashtra, India"

def random_offset():
    return random.uniform( 0.001 , 0.005 )

def process_location( location : str ):
    location = location.split( "-" )[0]
    location += LOCATION_SUFFIX
    location = location.title()
    return location

def get_lat_lng( address ):
    url = 'https://nominatim.openstreetmap.org/search/' + urllib.parse.quote(address) + '?format=json'
    response = requests.get( url ).json()[0]
    return float(response[ "lat" ]) + random_offset() , float(response["lon"]) + random_offset()

locations = [ process_location( location ) for location in locations ]
locations = locations[ 0 : 500 ]
coordinates = []

count = 0
for loc in locations:
    try:
        count += 1
        coord = get_lat_lng( loc )
        coordinates.append( coord )
        print( "Placed fetched:" , count )
    except Exception as e:
        print( e )
        continue

print( "Locations length:" , len( locations ) )

with open( "locations.pkl" , "wb" ) as file:
    pickle.dump( coordinates , file )