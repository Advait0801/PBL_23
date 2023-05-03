import pickle
import random

specialities = []
with open( "speciality/specialities.txt" , "r" ) as file:
    for line in file:
        specialities.append( line.strip() )

doctor_specialities = [ random.choice( specialities ) for _ in range( 500 ) ]
print( len( doctor_specialities ) )

with open( "specialities.pkl" , "wb" ) as file:
    pickle.dump( doctor_specialities , file )
