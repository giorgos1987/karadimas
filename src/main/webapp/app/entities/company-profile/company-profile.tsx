import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICompanyProfile } from 'app/shared/model/company-profile.model';
import { getEntities } from './company-profile.reducer';

export const CompanyProfile = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const companyProfileList = useAppSelector(state => state.companyProfile.entities);
  const loading = useAppSelector(state => state.companyProfile.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="company-profile-heading" data-cy="CompanyProfileHeading">
        <Translate contentKey="karadimastyresApp.companyProfile.home.title">Company Profiles</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="karadimastyresApp.companyProfile.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/company-profile/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="karadimastyresApp.companyProfile.home.createLabel">Create new Company Profile</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {companyProfileList && companyProfileList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="karadimastyresApp.companyProfile.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="karadimastyresApp.companyProfile.companyName">Company Name</Translate>
                </th>
                <th>
                  <Translate contentKey="karadimastyresApp.companyProfile.address1">Address 1</Translate>
                </th>
                <th>
                  <Translate contentKey="karadimastyresApp.companyProfile.address2">Address 2</Translate>
                </th>
                <th>
                  <Translate contentKey="karadimastyresApp.companyProfile.suburb">Suburb</Translate>
                </th>
                <th>
                  <Translate contentKey="karadimastyresApp.companyProfile.state">State</Translate>
                </th>
                <th>
                  <Translate contentKey="karadimastyresApp.companyProfile.postcode">Postcode</Translate>
                </th>
                <th>
                  <Translate contentKey="karadimastyresApp.companyProfile.phone">Phone</Translate>
                </th>
                <th>
                  <Translate contentKey="karadimastyresApp.companyProfile.mobile">Mobile</Translate>
                </th>
                <th>
                  <Translate contentKey="karadimastyresApp.companyProfile.email">Email</Translate>
                </th>
                <th>
                  <Translate contentKey="karadimastyresApp.companyProfile.emailInfo">Email Info</Translate>
                </th>
                <th>
                  <Translate contentKey="karadimastyresApp.companyProfile.logo">Logo</Translate>
                </th>
                <th>
                  <Translate contentKey="karadimastyresApp.companyProfile.language">Language</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {companyProfileList.map((companyProfile, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/company-profile/${companyProfile.id}`} color="link" size="sm">
                      {companyProfile.id}
                    </Button>
                  </td>
                  <td>{companyProfile.companyName}</td>
                  <td>{companyProfile.address1}</td>
                  <td>{companyProfile.address2}</td>
                  <td>{companyProfile.suburb}</td>
                  <td>{companyProfile.state}</td>
                  <td>{companyProfile.postcode}</td>
                  <td>{companyProfile.phone}</td>
                  <td>{companyProfile.mobile}</td>
                  <td>{companyProfile.email}</td>
                  <td>{companyProfile.emailInfo}</td>
                  <td>
                    {companyProfile.logo ? (
                      <div>
                        {companyProfile.logoContentType ? (
                          <a onClick={openFile(companyProfile.logoContentType, companyProfile.logo)}>
                            <img
                              src={`data:${companyProfile.logoContentType};base64,${companyProfile.logo}`}
                              style={{ maxHeight: '30px' }}
                            />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {companyProfile.logoContentType}, {byteSize(companyProfile.logo)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{companyProfile.language}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/company-profile/${companyProfile.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/company-profile/${companyProfile.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/company-profile/${companyProfile.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="karadimastyresApp.companyProfile.home.notFound">No Company Profiles found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default CompanyProfile;
