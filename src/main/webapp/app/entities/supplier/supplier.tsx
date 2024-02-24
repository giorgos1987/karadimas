import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISupplier } from 'app/shared/model/supplier.model';
import { getEntities } from './supplier.reducer';

export const Supplier = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const supplierList = useAppSelector(state => state.supplier.entities);
  const loading = useAppSelector(state => state.supplier.loading);
  const totalItems = useAppSelector(state => state.supplier.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (location.search !== endURL) {
      navigate(`${location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  return (
    <div>
      <h2 id="supplier-heading" data-cy="SupplierHeading">
        <Translate contentKey="karadimastyresApp.supplier.home.title">Suppliers</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="karadimastyresApp.supplier.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/supplier/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="karadimastyresApp.supplier.home.createLabel">Create new Supplier</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {supplierList && supplierList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="karadimastyresApp.supplier.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('company')}>
                  <Translate contentKey="karadimastyresApp.supplier.company">Company</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('webPage')}>
                  <Translate contentKey="karadimastyresApp.supplier.webPage">Web Page</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('notes')}>
                  <Translate contentKey="karadimastyresApp.supplier.notes">Notes</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lastName')}>
                  <Translate contentKey="karadimastyresApp.supplier.lastName">Last Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('firstName')}>
                  <Translate contentKey="karadimastyresApp.supplier.firstName">First Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('emailAddress')}>
                  <Translate contentKey="karadimastyresApp.supplier.emailAddress">Email Address</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('jobTitle')}>
                  <Translate contentKey="karadimastyresApp.supplier.jobTitle">Job Title</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('businessPhone')}>
                  <Translate contentKey="karadimastyresApp.supplier.businessPhone">Business Phone</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('homePhone')}>
                  <Translate contentKey="karadimastyresApp.supplier.homePhone">Home Phone</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('mobilePhone')}>
                  <Translate contentKey="karadimastyresApp.supplier.mobilePhone">Mobile Phone</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('faxNumber')}>
                  <Translate contentKey="karadimastyresApp.supplier.faxNumber">Fax Number</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('address')}>
                  <Translate contentKey="karadimastyresApp.supplier.address">Address</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('city')}>
                  <Translate contentKey="karadimastyresApp.supplier.city">City</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('stateProvince')}>
                  <Translate contentKey="karadimastyresApp.supplier.stateProvince">State Province</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('zipPostalCode')}>
                  <Translate contentKey="karadimastyresApp.supplier.zipPostalCode">Zip Postal Code</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('countryRegion')}>
                  <Translate contentKey="karadimastyresApp.supplier.countryRegion">Country Region</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('attachments')}>
                  <Translate contentKey="karadimastyresApp.supplier.attachments">Attachments</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {supplierList.map((supplier, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/supplier/${supplier.id}`} color="link" size="sm">
                      {supplier.id}
                    </Button>
                  </td>
                  <td>{supplier.company}</td>
                  <td>{supplier.webPage}</td>
                  <td>{supplier.notes}</td>
                  <td>{supplier.lastName}</td>
                  <td>{supplier.firstName}</td>
                  <td>{supplier.emailAddress}</td>
                  <td>{supplier.jobTitle}</td>
                  <td>{supplier.businessPhone}</td>
                  <td>{supplier.homePhone}</td>
                  <td>{supplier.mobilePhone}</td>
                  <td>{supplier.faxNumber}</td>
                  <td>{supplier.address}</td>
                  <td>{supplier.city}</td>
                  <td>{supplier.stateProvince}</td>
                  <td>{supplier.zipPostalCode}</td>
                  <td>{supplier.countryRegion}</td>
                  <td>
                    {supplier.attachments ? (
                      <div>
                        {supplier.attachmentsContentType ? (
                          <a onClick={openFile(supplier.attachmentsContentType, supplier.attachments)}>
                            <Translate contentKey="entity.action.open">Open</Translate>
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {supplier.attachmentsContentType}, {byteSize(supplier.attachments)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/supplier/${supplier.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/supplier/${supplier.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/supplier/${supplier.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="karadimastyresApp.supplier.home.notFound">No Suppliers found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={supplierList && supplierList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default Supplier;
