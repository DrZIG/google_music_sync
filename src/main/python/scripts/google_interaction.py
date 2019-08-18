import argparse
import io
import json
import os

from gmusicapi import Mobileclient, Webclient, Musicmanager

from scripts.utilities.argument_utilities import add_credentials_arguments, add_track_to_delete_argument, \
    add_track_to_upload_argument

login = ""
password = ""
authorize_file = ".\credentials"

def delete_songs(params):
    file_list = get_list_of_songs2(params.tracks_to_delete)
    client = authorize_mobile(params)
    if client:
        return client.delete_songs(file_list)

def upload_songs(params):
    file_list = get_list_of_songs2(params.tracks_to_upload)
    client = authorize_manager(params)
    if client:
        return client.upload(file_list)

def authorize_mobile(parameters):
    # api = Webclient()
    api = Mobileclient()
    # api = Musicmanager()
    # after running api.perform_oauth() once:
    # api.oauth_login('<a previously-registered device id>')
    # api.login(email=parameters.username, password=parameters.password)
    # => True
    if not os.path.exists(authorize_file):
        api.perform_oauth(authorize_file, True)

    api.oauth_login(Mobileclient.FROM_MAC_ADDRESS, authorize_file)
    if not api.is_authenticated():
        return None

    return api

def authorize_manager(parameters):
    # api = Webclient()
    # api = Mobileclient()
    api = Musicmanager()
    # after running api.perform_oauth() once:
    # api.oauth_login('<a previously-registered device id>')
    # api.login(email=parameters.username, password=parameters.password)
    # => True
    if not os.path.exists(authorize_file + '_manager'):
        api.perform_oauth(authorize_file + '_manager', True)

    api.login(authorize_file + '_manager')
    if not api.is_authenticated():
        return None

    return api

# def get_list_of_songs(file_name):
#     with open(file_name, 'r') as file:
#         file_lines = file.readlines()
#
#     lines = []
#     for line in file_lines:
#         result = line.replace("\n", "")
#         if result:
#             lines.append(result)
#
#     return lines

def get_list_of_songs2(file_name):
    result = []
    # with open(file_name, 'r') as file:
    #     line = file.readline().rstrip()
    #     while line:
    #         result.append(line)
    #         line = file.readline().rstrip()
    with io.open(file_name, encoding='utf-8') as file:
        for line in file:
            if line:
                result.append(line.rstrip())

    return result

def parse_params():
    parser = argparse.ArgumentParser(description='Google argument parser')
    subparsers = parser.add_subparsers()

    parser_delete = subparsers.add_parser('delete')
    add_credentials_arguments(parser_delete)
    add_track_to_delete_argument(parser_delete)

    parser_delete = subparsers.add_parser('upload')
    add_credentials_arguments(parser_delete)
    add_track_to_upload_argument(parser_delete)

    args = parser.parse_args()

    return args

def main():
    params = parse_params()

    if params.__contains__('tracks_to_delete'):
        result = delete_songs(params)
    if params.__contains__('tracks_to_upload'):
        result = upload_songs(params)

    if result:
        exit(json.dumps(result))
    else:
        exit(-1)

if __name__ == "__main__":
    main()