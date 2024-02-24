import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICustomerpayments } from 'app/shared/model/customerpayments.model';
import { getEntities } from './customerpayments.reducer';

export const Customerpayments = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const customerpaymentsList = useAppSelector(state => state.customerpayments.entities);
  const loading = useAppSelector(state => state.customerpayments.loading);
  const totalItems = useAppSelector(state => state.customerpayments.totalItems);

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
      <h2 id="customerpayments-heading" data-cy="CustomerpaymentsHeading">
        <Translate contentKey="karadimastyresApp.customerpayments.home.title">Customerpayments</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="karadimastyresApp.customerpayments.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/customerpayments/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="karadimastyresApp.customerpayments.home.createLabel">Create new Customerpayments</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {customerpaymentsList && customerpaymentsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="karadimastyresApp.customerpayments.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('ispaid')}>
                  <Translate contentKey="karadimastyresApp.customerpayments.ispaid">Ispaid</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('invoiceDate')}>
                  <Translate contentKey="karadimastyresApp.customerpayments.invoiceDate">Invoice Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dueDate')}>
                  <Translate contentKey="karadimastyresApp.customerpayments.dueDate">Due Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('tax')}>
                  <Translate contentKey="karadimastyresApp.customerpayments.tax">Tax</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('shipping')}>
                  <Translate contentKey="karadimastyresApp.customerpayments.shipping">Shipping</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('amountDue')}>
                  <Translate contentKey="karadimastyresApp.customerpayments.amountDue">Amount Due</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('notes')}>
                  <Translate contentKey="karadimastyresApp.customerpayments.notes">Notes</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('attachments')}>
                  <Translate contentKey="karadimastyresApp.customerpayments.attachments">Attachments</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lastupdated')}>
                  <Translate contentKey="karadimastyresApp.customerpayments.lastupdated">Lastupdated</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="karadimastyresApp.customerpayments.customerDetails">Customer Details</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {customerpaymentsList.map((customerpayments, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/customerpayments/${customerpayments.id}`} color="link" size="sm">
                      {customerpayments.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`karadimastyresApp.Paystatus.${customerpayments.ispaid}`} />
                  </td>
                  <td>
                    {customerpayments.invoiceDate ? (
                      <TextFormat type="date" value={customerpayments.invoiceDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {customerpayments.dueDate ? <TextFormat type="date" value={customerpayments.dueDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{customerpayments.tax}</td>
                  <td>{customerpayments.shipping}</td>
                  <td>{customerpayments.amountDue}</td>
                  <td>{customerpayments.notes}</td>
                  <td>
                    {customerpayments.attachments ? (
                      <div>
                        {customerpayments.attachmentsContentType ? (
                          <a onClick={openFile(customerpayments.attachmentsContentType, customerpayments.attachments)}>
                            <Translate contentKey="entity.action.open">Open</Translate>
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {customerpayments.attachmentsContentType}, {byteSize(customerpayments.attachments)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {customerpayments.lastupdated ? (
                      <TextFormat type="date" value={customerpayments.lastupdated} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {customerpayments.customerDetails ? (
                      <Link to={`/customer-details/${customerpayments.customerDetails.id}`}>{customerpayments.customerDetails.name}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/customerpayments/${customerpayments.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/customerpayments/${customerpayments.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/customerpayments/${customerpayments.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="karadimastyresApp.customerpayments.home.notFound">No Customerpayments found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={customerpaymentsList && customerpaymentsList.length > 0 ? '' : 'd-none'}>
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

export default Customerpayments;
