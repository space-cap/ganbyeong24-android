import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
import os

# Path to service account
cred_path = r"c:\workdir\space-cap\AndroidStudioProjects\Ganbyeong24\firebase-service-account.json"

if not os.path.exists(cred_path):
    print(f"Error: Service account file not found at {cred_path}")
    exit(1)

try:
    cred = credentials.Certificate(cred_path)
    firebase_admin.initialize_app(cred)

    db = firestore.client()

    print("Fetching collections...")
    collections = db.collections()
    
    count = 0
    print("\nFirestore Collections:")
    for collection in collections:
        print(f"- {collection.id}")
        count += 1

    if count == 0:
        print("No top-level collections found.")

except Exception as e:
    print(f"An error occurred: {e}")
