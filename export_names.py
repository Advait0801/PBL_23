import pandas as pd
import random
import pickle

male_names = pd.read_csv( "names/Indian-Male-Names.csv" , usecols=[ "name" ] )
male_names.dropna( inplace=True )
female_names = pd.read_csv( "names/Indian-Female-Names.csv" , usecols=[ "name" ] )
female_names.dropna( inplace=True )

male_names = male_names.values.reshape( ( -1 , ) ).tolist()
female_names = female_names.values.reshape( ( -1 , ) ).tolist()

def is_two_word_name( name ):
    return len( name.split() ) == 2

male_names = [ name.title() for name in male_names if is_two_word_name( name ) ]
female_names = [ name.title() for name in female_names if is_two_word_name( name ) ]

random.shuffle( male_names )
random.shuffle( female_names )

male_names = male_names[ 0 : 250 ]
female_names = female_names[ 0 : 250 ]

names = male_names + female_names
random.shuffle( names )

with open( "names.pkl" , "wb" ) as file:
    pickle.dump( names , file )

