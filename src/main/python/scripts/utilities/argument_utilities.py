
def add_credentials_arguments(group):
    _add_username_argument(group)
    _add_password_argument(group)
    _add_android_id_argument(group)

def add_track_to_delete_argument(group):
    group.add_argument('--tracks-to-delete', help='file with list of tracks')

def add_track_to_upload_argument(group):
    group.add_argument('--tracks-to-upload', help='file with list of tracks')

def _add_username_argument(group):
    group.add_argument('--username', help='Google user name.')

def _add_password_argument(group):
    group.add_argument('--password', help='Google user password.')

def _add_android_id_argument(group):
    group.add_argument('--imei', help='Android device id password.')
