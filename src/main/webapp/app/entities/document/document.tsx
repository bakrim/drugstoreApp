import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDocument } from 'app/shared/model/document.model';
import { getEntities } from './document.reducer';

export const Document = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const documentList = useAppSelector(state => state.document.entities);
  const loading = useAppSelector(state => state.document.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="document-heading" data-cy="DocumentHeading">
        <Translate contentKey="drugStoreApp.document.home.title">Documents</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="drugStoreApp.document.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/document/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="drugStoreApp.document.home.createLabel">Create new Document</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {documentList && documentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="drugStoreApp.document.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.document.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.document.thumbnailName">Thumbnail Name</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.document.mimeType">Mime Type</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.document.thumbnailPath">Thumbnail Path</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.document.downloadPath">Download Path</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.document.thumbnailDownloadPath">Thumbnail Download Path</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.document.uploadDate">Upload Date</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.document.path">Path</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.document.documentSize">Document Size</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {documentList.map((document, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/document/${document.id}`} color="link" size="sm">
                      {document.id}
                    </Button>
                  </td>
                  <td>{document.name}</td>
                  <td>{document.thumbnailName}</td>
                  <td>{document.mimeType}</td>
                  <td>{document.thumbnailPath}</td>
                  <td>{document.downloadPath}</td>
                  <td>{document.thumbnailDownloadPath}</td>
                  <td>{document.uploadDate ? <TextFormat type="date" value={document.uploadDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{document.path}</td>
                  <td>{document.documentSize}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/document/${document.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/document/${document.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/document/${document.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="drugStoreApp.document.home.notFound">No Documents found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Document;
