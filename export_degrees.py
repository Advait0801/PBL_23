import pickle
import random

degrees = [ "MBBS" , "MD" , "BAMS" , "BHMS" ]
doctor_degrees = [ random.choice( degrees ) for _ in range( 500 ) ]
print( len( doctor_degrees ) )

with open( "degrees.pkl" , "wb" ) as file:
    pickle.dump( doctor_degrees , file )